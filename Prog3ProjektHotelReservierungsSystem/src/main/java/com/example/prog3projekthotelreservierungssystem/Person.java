package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Eine abstrakte Klasse, die eine Person im Hotel repräsentiert.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {
    @Transient
    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname")
    private String vorname;
    @Column(name = "lastname")
    private String name;
    @Column
    private String email;
    @Column
    private LocalDate geburtsdatum;
    @Column(name = "telefonnummer")
    private String telefonNr;
    //@OneToMany(mappedBy = "gast")
    /*@Transient
    private List<Buchung> buchungList;*/

    /**
     * Konstruktor für eine Person im Hotel.
     *
     * @param vorname      Der Vorname der Person.
     * @param name         Der Nachname der Person.
     * @param email        Die E-Mail-Adresse der Person.
     * @param geburtsdatum Das Geburtsdatum der Person.
     * @param telefonNr    Die Telefonnummer der Person.
     * @throws HotelException Wenn einer der übergebenen Parameter ungültig ist.
     */
    public Person(String vorname, String name, String email,
                  LocalDate geburtsdatum, String telefonNr) throws HotelException {
        Validator.check(StringValidtor.stringCheckNullOrEmpty(vorname), "Vorname darf nicht leer sein");
        Validator.check(StringValidtor.stringCheckNullOrEmpty(name), "Name darf nicht leer sein");
        Validator.check(StringValidtor.stringCheckNullOrEmpty(email), "Email darf nicht leer sein");
        Validator.check(StringValidtor.stringCheckREGEX(email, EMAIL_REGEX), "Die Email ist ungültig");
        Validator.check(geburtsdatum == null, "Geburtsdatum darf nicht leer sein");
        Validator.check(telefonNr == null, "TelefonNr darf nicht leer sein");
        Validator.check(geburtsdatum.isAfter(LocalDate.now()), "Ungültige Eingabe");
        //später für anderen klassen
        this.vorname = vorname;
        this.name = name;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.telefonNr = telefonNr;
    }

    public Person() {

    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
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

    public void setEmail(String email) {
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

    /*public List<Buchung> getBuchungList() {
        return buchungList;
    }

    public void setBuchungList(List<Buchung> buchungList) {
        this.buchungList = buchungList;
    }*/

    public int getId() {
        return id;
    }

    /**
     * Überprüft, ob ein Objekt gleich dieser Person ist.
     *
     * @param o Das zu vergleichende Objekt.
     * @return true, wenn das Objekt gleich dieser Person ist, andernfalls false.
     */
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

    /**
     * Gibt den Hashcode dieser Person zurück.
     *
     * @return Der Hashcode der Person.
     */

    @Override
    public int hashCode() {
        int result = getVorname() != null ? getVorname().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getGeburtsdatum() != null ? getGeburtsdatum().hashCode() : 0);
        result = 31 * result + (getTelefonNr() != null ? getTelefonNr().hashCode() : 0);
        return result;
    }

    /**
     * Gibt eine String-Repräsentation dieser Person zurück.
     *
     * @return Eine String-Repräsentation der Person.
     */
    public String toString() {
        return this.getClass().getSimpleName() + "\nId: " + id +
                "\nName: " + vorname + " " + name + "\nGeburtsdatum: " + geburtsdatum
                + "\nEmail: " + email
                + "\nTelefonNr: " + telefonNr + "\n";
    }
}
