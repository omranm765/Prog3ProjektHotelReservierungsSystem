package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {
    @Transient
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
    @OneToMany(mappedBy = "gast")
    private List<Buchung> buchungList;

    public Person(String vorname, String name, String email,
                  LocalDate geburtsdatum, String telefonNr) throws HotelException {
        Validator.check(StringValidtor.stringCheckNullOrEmpty(vorname),"Vorname darf nicht leer sein");
        Validator.check(StringValidtor.stringCheckNullOrEmpty(name),"Name darf nicht leer sein");
        Validator.check(StringValidtor.stringCheckNullOrEmpty(email),"Email darf nicht leer sein");
        Validator.check(StringValidtor.stringCheckREGEX(email,EMAIL_REGEX),"Die Email ist ungültig");
        Validator.check(geburtsdatum == null,"Geburtsdatum darf nicht leer sein");
        Validator.check(telefonNr == null,"TelefonNr darf nicht leer sein");
        //später für anderen klassen
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

    public List<Buchung> getBuchungList() {
        return buchungList;
    }

    public void setBuchungList(List<Buchung> buchungList) {
        this.buchungList = buchungList;
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
