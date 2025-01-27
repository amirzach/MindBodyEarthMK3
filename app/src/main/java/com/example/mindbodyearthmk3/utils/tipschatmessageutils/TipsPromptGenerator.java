package com.example.mindbodyearthmk3.utils.tipschatmessageutils;

public class TipsPromptGenerator {
    public static String generateContextualPrompt(String userMessage) {
        return String.format(
                "You are a compassionate AI assistant providing holistic wellness guidance. " +
                        "Focus on three key areas: Mental Wellness, Body Fitness, and Environmental Consciousness. " +
                        "Provide scientifically-backed, empathetic, and actionable advice. " +
                        "User's query: %s " +
                        "Respond with integrated, constructive insights that connect these wellness dimensions.",
                userMessage
        );
    }
}