package jiajar.lollipop.com;

/**
 * Created by lollipop on 15/6/29.
 */

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class DelComInXml {

    private static final char TAG1 = '<';

    private static final char TAG2 = '!';

    private static final char TAG3 = '-';

    private static final char TAG4 = '>';

    //斜杠
    private static final int TYPE_TAG1 = 1;

    //星号
    private static final int TYPE_TAG2 = 2;

    // 双斜杠类型的注释
    private static final int TYPE_TAG3 = 3;

    //  /*的注释
    private static final int TYPE_TAG4 = 4;

    public static char[] del(char[] _target, int _start, int _end) {
        char[] tmp = new char[_target.length - (_end - _start + 1)];
        System.arraycopy(_target, 0, tmp, 0, _start);
        System.arraycopy(_target, _end + 1, tmp, _start, _target.length - _end
                - 1);
        return tmp;
    }

    public static String delComments(String _target) {
        int preType = 0;
        int cur = -1, token = -1;
        char[] input = _target.toCharArray();
        for (cur = 0; cur < input.length; cur++) {
            if (input[cur] == TAG1) {
                if (preType == TYPE_TAG1) {
                    preType = TYPE_TAG1;
                } else if ((input[cur + 1] == TAG2) && (input[cur + 2] == TAG3) && (input[cur + 3] == TAG3)) {
                    preType = TYPE_TAG1;
                    token = cur;
                }
            } else if (input[cur] == TAG4) {
                if (preType == TYPE_TAG1) {
                    if ((input[cur - 1] == TAG3) && (input[cur - 2] == TAG3)) {
                        input = del(input, token, cur);
                        cur = token - 1;
                        preType = 0;
                    }
                }
            }
        }
        return new String(input);
    }

    @Test
    public void test() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("输入目录：");
            String dirname = sc.next();
            File dir = new File(dirname);
            File[] listfile = dir.listFiles();
            for (int i = 0; i < listfile.length; i++) {
                BufferedReader reader = new BufferedReader(new FileReader(listfile[i]));
                StringBuilder content = new StringBuilder();
                String tmp = null;
                while ((tmp = reader.readLine()) != null) {
                    content.append(tmp);
                    content.append("\n");
                }
                String target = content.toString();
                String target1 = delComments(target);
                File f = new File(listfile[i] + ".bak");
                BufferedWriter output = new BufferedWriter(new FileWriter(f));
                output.write(target);
                output.close();
                BufferedWriter output1 = new BufferedWriter(new FileWriter(listfile[i]));
                output1.write(target1);
                output1.close();
            }
        } catch (Exception e) {

        }
    }
}
