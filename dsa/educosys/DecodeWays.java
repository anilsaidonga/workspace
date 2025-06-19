package educosys;

public class DecodeWays
{

    public static int helper(String s, int ind)
    {

        if (ind >= s.length()) return 1;

        if (s.charAt(ind) == '0') return 0;

        int ways = 0;

        // sigle digit case
        ways = helper(s, ind + 1);

        // double digit case
        if (ind < s.length() - 1)
        if (s.charAt(ind) == '1' || (s.charAt(1) == '2' && s.charAt(ind + 1) >= '0' && s.charAt(ind + 1) <= '6'))
            ways += helper(s, ind + 2);

        return ways;
    }

    public static int numDecodings(String s)
    {
        return helper(s, 0);
    }

    public static void main(String[] args)
    {
        System.out.println(numDecodings("26"));
    }

}
