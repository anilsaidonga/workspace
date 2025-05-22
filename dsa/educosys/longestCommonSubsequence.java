
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        String text1 = "bsbininm", text2 = "jmjkbkjkv";
        int m = text1.length(), n = text2.length();

        int prevText1Ind = 0, prevText2Ind = 0;
        int result = 0;

        if (m > n) {
            for (int i = 0; i < n; i++) { // no duplicates for now
                int ind = getIndIfPresent(text1, prevText1Ind, text2.charAt(i));
                if (ind != -1) {
                    result++;
                    prevText1Ind = ind;
                }
            }
        } else {
            for (int i = 0; i < m; i++) { // no duplicates for now
                int ind = getIndIfPresent(text2, prevText2Ind, text1.charAt(i));
                if (ind != -1) {
                    result++;
                    prevText2Ind = ind;
                }
            }
        }
    }

    public static int getIndIfPresent(String text1, int text1Ind, char c) {
        int ind = -1;
        for (int i = text1Ind; i < text1.length(); i++) {
            if (text1.charAt(i) == c) {
                ind = i;
                break;
            }
        }
        return ind;
    }

}