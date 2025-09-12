package interviewQuestions;

import java.util.Scanner;

public class CamelCasing
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        // trim
        s = s.trim();

        // split the string into words array
        String[] words = s.split("\\s+");

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < words.length; i++)
        {
            String word = words[i].toLowerCase();

            if (i == 0)
            {
                res.append(word);
                continue;
            }
            
            res.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }

        System.out.println(res);
        sc.close();
    }
}