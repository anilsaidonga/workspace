package educosys;

public class DecodeWays
{

    public static int helper(String s, int ind, int n)
    {
        if (ind >= n - 1) return 1; 

        int ways = 0;    

        // single digit    
        if (s.charAt(ind) != '0') 
        	ways += helper(s, ind + 1, n);    

        // double digit    
        if ((s.charAt(ind) == '1' || (s.charAt(ind) == '2' && s.charAt(ind + 1) <= '6')))     
            ways += helper(s, ind + 2, n);

        return ways;
    }
    public static int numDecodings(String s) {    
        if (s.charAt(0) == '0') return 0;    
        int n = s.length();
        return helper(s, 0, n);
    }

    public static void main(String[] args)
    {
        System.out.println(numDecodings("10"));
    }

}
