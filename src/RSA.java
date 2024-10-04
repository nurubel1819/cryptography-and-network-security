import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSA {

    private BigInteger n, d, e;
    private int bitlen = 1024;
    private SecureRandom r;

    public RSA(int bits) {
        bitlen = bits;
        r = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitlen / 2, r);
        BigInteger q = BigInteger.probablePrime(bitlen / 2, r);
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlen / 2, r);

        // Ensure that e and phi(n) are coprime
        while (phi.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    public RSA(BigInteger e, BigInteger d, BigInteger n) {
        this.e = e;
        this.d = d;
        this.n = n;
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(d, n);
    }

    public String toString() {
        return "n=" + n + "\ne=" + e + "\nd=" + d;
    }

    public static void main(String[] args) {
        RSA rsa = new RSA(1024);
        System.out.println("RSA Public and Private Keys: ");
        System.out.println(rsa);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message to encrypt:");
        String message = scanner.nextLine();
        BigInteger messageBytes = new BigInteger(message.getBytes());

        // Encrypt
        BigInteger encrypted = rsa.encrypt(messageBytes);
        System.out.println("Encrypted message: " + encrypted);

        // Decrypt
        BigInteger decrypted = rsa.decrypt(encrypted);
        String decryptedMessage = new String(decrypted.toByteArray());
        System.out.println("Decrypted message: " + decryptedMessage);

        scanner.close();
    }
}
