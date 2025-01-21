package com.example.mindbodyearthmk3.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class CustomChartView extends View {
    private Paint linePaint;
    private Paint pointPaint;
    private Paint textPaint;
    private List<Integer> dataPoints;

    public CustomChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize paints
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
    }

    public void setDataPoints(List<Integer> dataPoints) {
        this.dataPoints = dataPoints;
        invalidate(); // Redraw the chart when data changes
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dataPoints == null || dataPoints.isEmpty()) {
            return;
        }

        float width = getWidth();
        float height = getHeight();
        float padding = 50f;
        float stepX = (width - 2 * padding) / (dataPoints.size() - 1);
        float maxValue = getMaxValue();
        float stepY = (height - 2 * padding) / maxValue;

        // Draw chart lines and points
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            float startX = padding + i * stepX;
            float startY = height - padding - (dataPoints.get(i) * stepY);
            float endX = padding + (i + 1) * stepX;
            float endY = height - padding - (dataPoints.get(i + 1) * stepY);

            // Draw line
            canvas.drawLine(startX, startY, endX, endY, linePaint);

            // Draw points
            canvas.drawCircle(startX, startY, 8f, pointPaint);
        }

        // Draw the last point
        float lastX = padding + (dataPoints.size() - 1) * stepX;
        float lastY = height - padding - (dataPoints.get(dataPoints.size() - 1) * stepY);
        canvas.drawCircle(lastX, lastY, 8f, pointPaint);
    }

    private float getMaxValue() {
        float max = Float.MIN_VALUE;
        for (int value : dataPoints) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
