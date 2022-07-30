import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;

public class ROT13 {
    String inputIndex = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String outputIndex;
    String customIndex = "GHIXYZABCTUVDEFQRSJKLMNOPWghixyzabctuvdefqrsjklmnopw";
    Boolean ioSwitch;
    Boolean customSwitch = false;


    ROT13(Character cs, Character cf) {
        this.outputIndex = "";
        int diff;
        for (int i = 0; i < inputIndex.length(); i++) {
            diff = cf - cs;
            if (inputIndex.charAt(i) >= 65 && inputIndex.charAt(i) <= 90 && inputIndex.charAt(i) + diff > 90 || inputIndex.charAt(i) >= 97 && inputIndex.charAt(i) <= 122 && inputIndex.charAt(i) + diff > 122) {
                diff -= 26;
            }
            outputIndex += inputIndex.charAt(i + diff);
        }
    }


    ROT13() {
    }


    public String crypt(String text) {
        String newText = "";
        String inputKey;
        String outputKey;
        if (customSwitch == true) {
            inputKey = inputIndex;
            outputKey = customIndex;
        } else {
            if (ioSwitch == null) {
                ioSwitch = true;
            }
            if (ioSwitch == true) {
                inputKey = inputIndex;
                outputKey = outputIndex;
            } else {
                inputKey = outputIndex;
                outputKey = inputIndex;
            }
        }

        for (int i = 0; i < text.length(); i++) {
            int indexOfCurrent = inputKey.indexOf(text.charAt(i));
            if (indexOfCurrent >= 0) {
                newText += outputKey.charAt(indexOfCurrent);
            } else {
                newText += text.charAt(i);
            }

        }

        return newText;
    }

    public String encrypt(String text) {
        ioSwitch = true;
        return crypt(text);
    }

    public String decrypt(String text) {
        ioSwitch = false;
        return crypt(text);
    }

    public String caesarCipher(String text) {
        return rotate(text, 'X');
    }

    public static String rotate(String s, Character c) {
        ROT13 rot13 = new ROT13('A', c);
        return rot13.encrypt(s);
    }

    public void cryptFile() throws IOException {
        File inF = new File("sonnet18.txt");
        File outF = new File("sonnet18.enc");
        String toBeModified = "";
        BufferedReader reader = new BufferedReader(new FileReader(inF));
        String line = reader.readLine();

        while(line != null)
        {
            toBeModified += line + System.lineSeparator();
            line = reader.readLine();
        }
        customSwitch = true;
        String customString = encrypt(toBeModified);
        String caeserString = caesarCipher(toBeModified);
        String modified = customString + "\n\n" + caeserString;
        FileWriter writer = new FileWriter(outF);
        writer.write(modified);
        reader.close();
        writer.close();
    }

}


