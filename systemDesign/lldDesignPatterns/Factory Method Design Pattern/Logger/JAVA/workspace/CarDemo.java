package workspace;

interface Car
{
    void drive();
}

class Sedan implements Car
{
    @Override
    public void drive() {
        System.out.println("Driving a Sedan");
    }
}

class SUV implements Car
{
    @Override
    public void drive() {
        System.out.println("Driving an SUV");
    }
}

class Truck implements Car
{
    @Override
    public void drive() {
        System.out.println("Driving a Truck");
    }
}

class MiniTruck implements Car
{
    @Override
    public void drive() {
        System.out.println("Driving a Mini Truck");
    }
}

abstract class CarFactory
{
    abstract Car createCar();
}

class SedanFactory extends CarFactory
{
    @Override
    Car createCar() {
        return new Sedan();
    }
}   

class SUVFactory extends CarFactory
{
    @Override
    Car createCar() {
        return new SUV();
    }
}   

class TruckFactory extends CarFactory
{
    @Override
    Car createCar() {
        return new Truck();
    }
}   

class MiniTruckFactory extends CarFactory
{
    @Override
    Car createCar() {
        return new MiniTruck();
    }
}

public class CarDemo {

    public static void main(String[] args)
    {
        CarFactory carFactory = new MiniTruckFactory();
        Car car = carFactory.createCar();
        car.drive();
    }
}
