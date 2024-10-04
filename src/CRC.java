import java.util.Scanner;

public class CRC {

    // Method to perform CRC calculation
    public static String computeCRC(String input, String generator) {
        // Append zeros to the input (degree of generator - 1)
        int inputLength = input.length();
        int generatorLength = generator.length();
        String dividend = input + "0".repeat(generatorLength - 1);

        // Convert the strings to arrays for bitwise manipulation
        char[] dividendBits = dividend.toCharArray();
        char[] generatorBits = generator.toCharArray();

        // Perform the division (XOR process)
        for (int i = 0; i <= dividendBits.length - generatorLength; i++) {
            if (dividendBits[i] == '1') {
                // XOR the generator with the current bits in dividend
                for (int j = 0; j < generatorLength; j++) {
                    dividendBits[i + j] = dividendBits[i + j] == generatorBits[j] ? '0' : '1';
                }
            }
        }

        // Extract the remainder from the dividend (last generatorLength - 1 bits)
        return new String(dividendBits, inputLength, generatorLength - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the binary data
        System.out.println("Enter the binary data (message): ");
        String inputData = scanner.nextLine();

        // Input the binary generator polynomial
        System.out.println("Enter the generator polynomial (binary): ");
        String generatorPolynomial = scanner.nextLine();

        // Compute CRC
        String crc = computeCRC(inputData, generatorPolynomial);
        System.out.println("CRC value: " + crc);

        // Append CRC to the original data
        String transmittedMessage = inputData + crc;
        System.out.println("Transmitted message (data + CRC): " + transmittedMessage);

        scanner.close();
    }
}
