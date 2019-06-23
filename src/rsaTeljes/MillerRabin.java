package rsaTeljes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MillerRabin {

    public static boolean millerRabin(BigInteger nagySzam){
        int primszamlalo = 0; //A harom vizsgalatbol hany szerint prim, ez alapjan hozzuk meg a dontest
        List<BigInteger> tanuk = new ArrayList<>();
        BigInteger tanu;
        for (int i = 0; i < 3; i++) { // Harom ellenorzest vegzunk
            BigInteger egyelKisebb = nagySzam.subtract(BigInteger.ONE);
            int s = 0;
            do {
                tanu = BigInteger.valueOf((int) (Math.random() * 19 + 2)); //Veletlenszeruen valasztjuk ki a 3 tanut,
            }while (tanuk.contains(tanu) || nagySzam.compareTo(tanu) <= 0); //Kizarjuk az ismetlodest, illetve azt, hogy a tanu a vizsgalt szamnal nagyobb vagy egyenlo legyen, bar ennek itt nincs jelentosege
            tanuk.add(tanu);
            while (egyelKisebb.mod(tanu).equals(BigInteger.ZERO)) {
                s++;
                egyelKisebb = egyelKisebb.divide(tanu);
            }
            if(ellenoriz(nagySzam, tanu, s, egyelKisebb))
                primszamlalo++;
        }return (primszamlalo > 0 ? true : false);
    }

    public static boolean ellenoriz(BigInteger nagySzam, BigInteger tanu, int s, BigInteger egyelKisebb) {
        for (int r = 0; r < s; r++) {
            BigInteger kitevo = (BigInteger.TWO.pow(r)).multiply(egyelKisebb);
            BigInteger elsoEllenorzes = tanu.modPow(kitevo, nagySzam);
            if (elsoEllenorzes.equals(BigInteger.valueOf(-1))) {
                return true;
            }
        }
        BigInteger masodikEllenorzes = tanu.modPow(egyelKisebb, nagySzam);
        if (masodikEllenorzes.equals(BigInteger.valueOf(1))){
            return true;
        }
        return false;
    }

}
