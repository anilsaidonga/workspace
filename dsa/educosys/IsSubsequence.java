package educosys;

public class IsSubsequence {

    public static int getInd(char c, String t, int startInd)
    {
        int ind = -1;
        for (int i = startInd; i < t.length(); i++)
        {
            if (c == t.charAt(i))
            	return ind = i;
        }
        return ind;
    }
    public static boolean isSubsequence(String s, String t) {
        int startInd = 0;
        for (int i = 0; i < s.length(); i++)
        {
            startInd = getInd(s.charAt(i), t, startInd);
            if (startInd == -1) return false;
            startInd++;
        }
        return true;
    }
    
    public static void main(String[] args)
    {
    	System.out.println(isSubsequence("aza", "abzba"));
    }
}
