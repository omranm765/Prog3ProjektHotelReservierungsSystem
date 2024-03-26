package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname")
    private final String vorname;
    @Column(name = "lastname")
    private final String name;
    @Column
    private String email;
    @Column
    private final LocalDate geburtsdatum;
    @Column(name = "telefonnummer")
    private String telefonNr;

    public Person(String vorname, String name, String email,
                  LocalDate geburtsdatum, String telefonNr) throws HotelException {
        if (vorname == null || vorname.trim().isEmpty()) {
            throw new HotelException("Vorname darf nicht leer sein");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new HotelException("Name darf nicht leer sein");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new HotelException("Email darf nicht leer sein");
        }
        if (!email.matches(EMAIL_REGEX)){
            throw new HotelException("Die Email ist ungültig");
        }
        if (geburtsdatum == null){
            throw new HotelException("Geburtsdatum darf nicht leer sein");
        }
        if (telefonNr == null){
            throw new HotelException("TelefonNr darf nicht leer sein");
        }
        this.vorname = vorname;
        this.name = name;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.telefonNr = telefonNr;
    }

    public String getVorname() {
        return vorname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getTelefonNr() {
        return telefonNr;
    }

    public void setTelefonNr(String telefonNr) {
        this.telefonNr = telefonNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getVorname() != null ? !getVorname().equals(person.getVorname()) : person.getVorname() != null)
            return false;
        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) return false;
        if (getEmail() != null ? !getEmail().equals(person.getEmail()) : person.getEmail() != null) return false;
        if (getGeburtsdatum() != null
                ? !getGeburtsdatum().equals(person.getGeburtsdatum()) : person.getGeburtsdatum() != null)
            return false;
        return getTelefonNr() != null ? getTelefonNr().equals(person.getTelefonNr()) : person.getTelefonNr() == null;
    }

    @Override
    public int hashCode() {
        int result = getVorname() != null ? getVorname().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getGeburtsdatum() != null ? getGeburtsdatum().hashCode() : 0);
        result = 31 * result + (getTelefonNr() != null ? getTelefonNr().hashCode() : 0);
        return result;
    }

    public String toString() {
        return this.getClass().getSimpleName() +
                "\nName: " + vorname + " " + name + "\nGeburtsdatum: " + geburtsdatum
                + "\nEmail: " + email
                + "\nTelefonNr: " + telefonNr;
    }
}
