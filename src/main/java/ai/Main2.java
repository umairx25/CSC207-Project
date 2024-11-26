package ai;

import javax.swing.*;

public class Main2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("AI Chat Application");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AiClient aiClient = new GenerativeAi();
        ChatBotPanel chatBotPanel = new ChatBotPanel(aiClient);
        frame.add(chatBotPanel);

        frame.setVisible(true);
    }
}
