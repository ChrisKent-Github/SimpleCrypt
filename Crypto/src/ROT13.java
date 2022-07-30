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
        rotate(text, 'X');
        return crypt(text);
    }

    public static String rotate(String s, Character c) {
        ROT13 rot13 = new ROT13('A', c);
        return rot13.encrypt(s);
    }

    public void cryptFile() throws IOException {
        customSwitch = true;
        File inF = new File("sonnet18.txt");
        File outF = new File("sonnet18.enc");
        FileReader ins = null;
        FileWriter outs = null;
        try {
            ins = new FileReader(inF);
            outs = new FileWriter(outF);
            int ch;
            while ((ch = ins.read()) != -1) {
                outs.write(ch);
            }
        } catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        } finally {
            try {
                ins.close();
                outs.close();
            } catch (IOException e) {
            }
        }




    }




}








//        Path pathFrom = Paths.get("/Users/chris/Projects/SimpleCrypt/sonnet18.txt");
//        Path pathTo = Paths.get("/Users/chris/Projects/SimpleCrypt/sonnet18.enc");
//        String file2Txt = String.valueOf(Files.readAllBytes(pathFrom));
//        customSwitch = true;
//        String customString = encrypt(file2Txt);
//        String caeserString = caesarCipher(file2Txt);
//        String result = customString + "\n\n" + caeserString;
//        Files.writeString(pathTo, result.toString(), StandardCharsets.UTF_8);

