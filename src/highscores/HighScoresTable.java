package highscores;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HighScoresTable {

    // שם הקובץ בדיוק כמו שהמטלה דורשת
    private static final String FILE_NAME = "highscores.txt";

    // השיא שנמצא "בזיכרון" בזמן ריצה
    private int highScore;

    public HighScoresTable() {
        this.highScore = 0;
    }

    public int getHighScore() {
        return this.highScore;
    }

    /**
     * קורא את השיא מקובץ highscores.txt שנמצא בתיקיית ההרצה.
     * אם אין קובץ - השיא נשאר 0.
     */
    public void loadFromFile() {
        File file = new File(FILE_NAME);

        // אם לא קיים עדיין -> זו הרצה ראשונה, שיא 0
        if (!file.exists()) {
            this.highScore = 0;
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            // הקובץ אמור להכיל שורה אחת בלבד
            String line = reader.readLine();
            if (line == null) {
                this.highScore = 0;
                return;
            }

            // פורמט צפוי: "The highest score so far is: XXX."
            int colonIndex = line.lastIndexOf(':');
            int dotIndex = line.lastIndexOf('.');

            // בדיקת תקינות בסיסית
            if (colonIndex == -1 || dotIndex == -1 || dotIndex <= colonIndex) {
                this.highScore = 0;
                return;
            }

            // מוציאים את החלק של המספר בין ':' ל-'.'
            String numberPart = line.substring(colonIndex + 1, dotIndex).trim();

            // המרה ל-int
            this.highScore = Integer.parseInt(numberPart);

        } catch (Exception e) {
            System.out.println("=== DEBUG: saveToFile failed ===");
            e.printStackTrace();
        }

    }

    /**
     * שומר את השיא הנוכחי לקובץ highscores.txt בתיקיית ההרצה,
     * בפורמט שהמטלה דורשת.
     */
    public void saveToFile() {
        File file = new File(FILE_NAME);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            writer.write("The highest score so far is: " + this.highScore + ".");
            writer.newLine();

        } catch (Exception e) {
            System.out.println("=== DEBUG: saveToFile failed ===");
            e.printStackTrace();
        }

    }

    /**
     * אם score החדש גבוה מהשיא - מעדכן את השיא בזיכרון ומחזיר true.
     * אחרת לא משנה ומחזיר false.
     */
    public boolean updateIfHigher(int currentScore) {
        if (currentScore > this.highScore) {
            this.highScore = currentScore;
            return true;
        }
        return false;
    }
}
