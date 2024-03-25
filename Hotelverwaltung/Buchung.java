package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name ="Service")
@Entity
public class Service {
    private final String serviceName;
    private int serviceID;
    private double servicePreis;
    private static int nextServiceGeneratedID = 0;

    public Service(String serviceName, double servicePreis) throws HotelException {
        if (serviceName == null || serviceName.trim().isEmpty()){
            throw new HotelException("Service Name darf nicht leer sein");
        }
        if (servicePreis < 0.0000001 || servicePreis > 1.0000001){
            throw new HotelException("Preis darf nicht 0 sein");
        }
        this.serviceName = serviceName;
        this.servicePreis = servicePreis;
        this.serviceID = generate();
    }

    private static int generate(){
        return nextServiceGeneratedID++;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServicePreis() {
        return servicePreis;
    }

    public String toString(){
        return "ServiceName: " + serviceName + "\nServicePreis: " + servicePreis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (Double.compare(service.getServicePreis(), getServicePreis()) != 0) return false;
        return getServiceName() != null ? getServiceName().equals(service.getServiceName()) : service.getServiceName() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getServiceName() != null ? getServiceName().hashCode() : 0;
        temp = Double.doubleToLongBits(getServicePreis());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
