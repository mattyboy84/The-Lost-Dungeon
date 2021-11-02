package com.company;

public class Spells {
    private String SpellName;
    private String Element;
    private int Damage;
    private int ManaCost;


    public Spells(String SpellName, String Element, int Damage, int ManaCost) {
        this.SpellName = SpellName;
        this.Element = Element;
        this.Damage = Damage;
        this.ManaCost = ManaCost;
    }

    public Spells(){
        System.out.println("ERROR");    }

    public String getSpellName() {
        return SpellName;
    }

    public String getElement() {
        return Element;
    }

    public int getDamage() {
        return Damage;
    }

    public int getManaCost() {
        return ManaCost;
    }
}
