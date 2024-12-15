package pustrace.elibraryjavaparser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Текстовое поле для пути к входному файлу
        TextField inputFileField = new TextField();
        inputFileField.setPromptText("Введите путь к входному файлу");

        // Текстовое поле для пути к выходному файлу
        TextField outputFileField = new TextField();
        outputFileField.setPromptText("Введите путь к выходному файлу");

        // Текстовое поле для ввода времени ожидания (для решения капчи)
        TextField captchaDelayField = new TextField();
        captchaDelayField.setPromptText("Введите задержку до получения куки (мс)");
        captchaDelayField.setText("3000"); // Дефолтное значение
        captchaDelayField.setVisible(false);

        // Текстовое поле для ввода времени задержки между страницами
        TextField pageDelayField = new TextField();
        pageDelayField.setPromptText("Введите задержку между страницами (мс)");
        pageDelayField.setText("3000"); // Дефолтное значение
        pageDelayField.setVisible(false);

        // Кнопка "Для разработчиков", которая скрывает/показывает поля задержки
        Button devButton = new Button("Для разработчиков");
        devButton.setOnAction(event -> {
            boolean isVisible = captchaDelayField.isVisible();
            captchaDelayField.setVisible(!isVisible);
            pageDelayField.setVisible(!isVisible);
        });

        // Кнопка для запуска обработки
        Button btn = new Button("Запустить обработку");
        btn.setOnAction(event -> {
            // Получаем пути из текстовых полей
            String inputFilePath = inputFileField.getText();
            String outputFilePath = outputFileField.getText();

            // Проверка пути к входному файлу
            if (inputFilePath.isEmpty()) {
                showAlert("Ошибка", "Путь к входному файлу не может быть пустым.");
                return;
            }

            // Проверка формата входного файла
            if (!inputFilePath.endsWith(".txt")) {
                showAlert("Ошибка", "Входной файл должен быть формата .txt.");
                return;
            }

            // Установка дефолтного пути для выходного файла, если он не указан
            if (outputFilePath.isEmpty()) {
                outputFilePath = "output.json";
            }

            // Проверка формата выходного файла
            if (!outputFilePath.endsWith(".json")) {
                showAlert("Ошибка", "Выходной файл должен быть формата .json.");
                return;
            }

            // Получаем значение задержки для капчи
            int captchaDelay = 0;
            try {
                captchaDelay = Integer.parseInt(captchaDelayField.getText());
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Задержка для капчи должна быть числом.");
                return;
            }

            // Получаем значение задержки между страницами
            int pageDelay = 0;
            try {
                pageDelay = Integer.parseInt(pageDelayField.getText());
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Задержка между страницами должна быть числом.");
                return;
            }

            // Запуск основного процесса обработки с указанными параметрами
            Main.processAuthors(inputFilePath, outputFilePath, captchaDelay, pageDelay);
        });

        // Размещение элементов на экране (используем VBox для вертикального размещения)
        VBox root = new VBox(10);  // 10 - это расстояние между элементами
        root.getChildren().addAll(inputFileField, outputFileField, devButton, captchaDelayField, pageDelayField, btn);

        // Создание сцены с размерами 400x300
        Scene scene = new Scene(root, 400, 300);

        // Настройка и показ окна
        primaryStage.setTitle("Графический интерфейс для обработки данных");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод для отображения всплывающего окна с сообщением
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
