package com.example.mindbodyearthmk3.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class CustomChartView extends View {
    private Paint bloodPressurePaint;
    private Paint heartRatePaint;
    private Paint weightPaint;
    private Paint pointPaint;
    private Paint textPaint;
    private Paint legendPaint;

    private List<Integer> bloodPressureData;
    private List<Integer> heartRateData;
    private List<Float> weightData;

    public CustomChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize paints for lines
        bloodPressurePaint = new Paint();
        bloodPressurePaint.setColor(Color.RED);
        bloodPressurePaint.setStrokeWidth(5f);
        bloodPressurePaint.setStyle(Paint.Style.STROKE);

        heartRatePaint = new Paint();
        heartRatePaint.setColor(Color.BLUE);
        heartRatePaint.setStrokeWidth(5f);
        heartRatePaint.setStyle(Paint.Style.STROKE);

        weightPaint = new Paint();
        weightPaint.setColor(Color.GREEN);
        weightPaint.setStrokeWidth(5f);
        weightPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);

        legendPaint = new Paint();
        legendPaint.setStyle(Paint.Style.FILL);
    }

    public void setDataPoints(List<Integer> bloodPressure, List<Integer> heartRate, List<Float> weight) {
        this.bloodPressureData = bloodPressure;
        this.heartRateData = heartRate;
        this.weightData = weight;
        invalidate(); // Redraw the chart when data changes
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if ((bloodPressureData == null || bloodPressureData.isEmpty()) ||
                (heartRateData == null || heartRateData.isEmpty()) ||
                (weightData == null || weightData.isEmpty())) {
            return;
        }

        float width = getWidth();
        float height = getHeight();
        float padding = 50f;

        int maxPoints = Math.max(bloodPressureData.size(),
                Math.max(heartRateData.size(), weightData.size()));
        float stepX = (width - 2 * padding) / (maxPoints - 1);
        float maxValue = Math.max(getMaxValue(bloodPressureData),
                Math.max(getMaxValue(heartRateData), getMaxValue(weightData)));
        float stepY = (height - 2 * padding) / maxValue;

        // Draw lines for blood pressure, heart rate, and weight
        drawLineChart(canvas, bloodPressureData, stepX, stepY, height, padding, bloodPressurePaint);
        drawLineChart(canvas, heartRateData, stepX, stepY, height, padding, heartRatePaint);
        drawLineChart(canvas, weightData, stepX, stepY, height, padding, weightPaint);

        // Draw legend
        drawLegend(canvas, width, height, padding);
    }

    private void drawLineChart(Canvas canvas, List<?> data, float stepX, float stepY,
                               float height, float padding, Paint paint) {
        for (int i = 0; i < data.size() - 1; i++) {
            float startX = padding + i * stepX;
            float startY = height - padding - (getValue(data.get(i)) * stepY);
            float endX = padding + (i + 1) * stepX;
            float endY = height - padding - (getValue(data.get(i + 1)) * stepY);

            // Draw line
            canvas.drawLine(startX, startY, endX, endY, paint);

            // Draw points
            canvas.drawCircle(startX, startY, 8f, pointPaint);
        }

        // Draw the last point
        float lastX = padding + (data.size() - 1) * stepX;
        float lastY = height - padding - (getValue(data.get(data.size() - 1)) * stepY);
        canvas.drawCircle(lastX, lastY, 8f, pointPaint);
    }

    private void drawLegend(Canvas canvas, float width, float height, float padding) {
        float legendPadding = 20f;
        float legendX = padding;
        float legendY = height - padding + 30f;

        // Blood Pressure Legend
        legendPaint.setColor(Color.RED);
        canvas.drawRect(legendX, legendY, legendX + 30f, legendY + 30f, legendPaint);
        canvas.drawText("Blood Pressure", legendX + 40f, legendY + 25f, textPaint);

        // Heart Rate Legend
        legendX += 200f;
        legendPaint.setColor(Color.BLUE);
        canvas.drawRect(legendX, legendY, legendX + 30f, legendY + 30f, legendPaint);
        canvas.drawText("Heart Rate", legendX + 40f, legendY + 25f, textPaint);

        // Weight Legend
        legendX += 200f;
        legendPaint.setColor(Color.GREEN);
        canvas.drawRect(legendX, legendY, legendX + 30f, legendY + 30f, legendPaint);
        canvas.drawText("Weight", legendX + 40f, legendY + 25f, textPaint);
    }

    private float getMaxValue(List<?> data) {
        float max = Float.MIN_VALUE;
        for (Object value : data) {
            if (getValue(value) > max) {
                max = getValue(value);
            }
        }
        return max;
    }

    private float getValue(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Float) {
            return (Float) value;
        }
        return 0;
    }
}
