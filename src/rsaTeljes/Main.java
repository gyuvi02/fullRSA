package rsaTeljes;

import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        BigInteger p = primGeneralas();
        System.out.println("Az elso prim = " + p);
        BigInteger q = primGeneralas();
        System.out.println("A masodik prim = " + q);
        BigInteger n = p.multiply(q);
        System.out.println("n = " + n);
        BigInteger fin = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
        System.out.println("fi(n) = " + fin);
        BigInteger e = eKereses(fin);
        System.out.println("e = " +e);

        BigInteger[] tabla = kea(e, fin);
        BigInteger d = tabla[1];
        if (d.compareTo(BigInteger.ZERO) < 0) {
            d = d.add(fin);
        }
        System.out.println("d = " + d);

        BigInteger m = BigInteger.valueOf(9876);
        System.out.println("A titkositando adat = " + m);

        BigInteger c = titkositas(m, e, n);
        System.out.println("A titkositott adat = " + c);

        BigInteger vegeredmeny = visszafejtes(c, d, n);
        System.out.println("A visszafejtett adat = " + vegeredmeny);
    }

    //A primszamok generalasa
    public static BigInteger primGeneralas() {
        BigInteger nagySzam;
        while (true) {
            do {
                nagySzam = new BigInteger(64, new Random()); //Egy viszonylag kis primet generalunk
            } while ((nagySzam.equals(BigInteger.ONE)
                    || nagySzam.equals(BigInteger.TWO)
                    || nagySzam.equals(BigInteger.valueOf(3))//Ezeknek itt nincs gyakorlati jelentosege
                    || nagySzam.mod(BigInteger.TWO).equals(BigInteger.ZERO))); //A paros szamok kizarasa
            if (MillerRabin.millerRabin(nagySzam))
                return nagySzam;
        }

    }

    //Legnagyobb kozos oszto
    public static BigInteger lnko(BigInteger p, BigInteger q)
    {
        BigInteger r = p.mod(q);
        if (r.equals(BigInteger.ZERO)) return q;
        return lnko(q, r);
    }

    //Keressuk az e-t
    public static BigInteger eKereses(BigInteger fin) {
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(fin) < 0; i = i.add(BigInteger.ONE)){
            if (lnko(i,fin).equals(BigInteger.ONE))
                return i;
        }return fin;
    }

    //Kiterjesztett euklideszi algoritmus
    static BigInteger[] kea(BigInteger e, BigInteger fin) {
        if (fin.equals(BigInteger.ZERO))
            return new BigInteger[] { e, BigInteger.ONE, BigInteger.ZERO };
        else {

            BigInteger[] eeaAdatok = kea(fin, e.mod(fin));
            BigInteger tmp = eeaAdatok[1].subtract(eeaAdatok[2].multiply(e.divide(fin)));
            eeaAdatok[1] = eeaAdatok[2];
            eeaAdatok[2] = tmp;
            return eeaAdatok;
        }
    }

    //Titkositas
    public static BigInteger titkositas(BigInteger titkositando, BigInteger e, BigInteger n) {
        BigInteger c = titkositando.modPow(e,n );
        return c;
    }

    //Visszafejtes
    public static BigInteger visszafejtes(BigInteger c, BigInteger d, BigInteger n) {
        BigInteger m = c.modPow(d, n);
        return m;
    }


}
