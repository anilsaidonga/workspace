public class Median
{
    public static double giveMeMedian(int[] arr)
    {
        int n = arr.length;
        int sum = 0;
        if (n % 2 == 0)
        {
         sum = arr[n/2] + arr[n/2 - 1];
            return (double) sum / 2;
        }
        return (double) arr[n/2];
    }
    public static void main(String[] args)
    {
        //int[] arr = {1, 3, 3, 6, 7, 8, 9};
        int[] arr = {1, 2, 3, 4, 5, 6, 8, 9};
        System.out.println(giveMeMedian(arr));
    }
}

employee table

empid, empName, salary

select * employee order by salary desc offset 3 limit 1