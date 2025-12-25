package workspace;

interface ICar {
    void drive();
}

class MarutiSuziki implements ICar
{
    @Override
    public void drive()
    {
        System.out.println("Driving Maruti Suziki");
    }
}

class Nissan implements ICar
{
    @Override
    public void drive()
    {
        System.out.println("Driving Nissan");
    }
}

class Kia implements ICar
{
    @Override
    public void drive()
    {
        System.out.println("Driving Kia");
    }
}

class Tata implements ICar
{
    @Override
    public void drive()
    {
        System.out.println("Driving Tata");
    }
}

class CarFactory
{
    public static ICar createCar(String brandName)
    {
        if (brandName.equalsIgnoreCase("Maruti Suziki"))
        {
            return new MarutiSuziki();
        }
        else if (brandName.equalsIgnoreCase("Nissan"))
        {
            return new Nissan();
        }
        else if (brandName.equalsIgnoreCase("Kia"))
        {
            return new Kia();
        }
        else if (brandName.equals("Tata"))
        {
            return new Tata();
        }
        else
        {
            throw new IllegalArgumentException("Unknown car brand: " + brandName);
        }
    }
}

public class CarDemo {
    public static void main(String[] args)
    {
        ICar car = CarFactory.createCar("Tata");
        car.drive();
    }
}
