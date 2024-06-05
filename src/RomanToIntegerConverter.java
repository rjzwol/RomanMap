import java.util.HashMap;

public class RomanToIntegerConverter {

    private static final HashMap<Character, Integer> romanMap = new HashMap<>();

    static {
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
    }

    public static int romanToInt(String s) {
        int result = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int value = romanMap.get(s.charAt(i));

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    public static boolean isValidRomanNumeral(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        int prevValue = Integer.MAX_VALUE;
        int repetitionCount = 0;
        int result = 0; // Declaring result here

        for (char c : s.toCharArray()) {
            int value = romanMap.getOrDefault(c, 0);

            if (value == 0) {
                return false; // Invalid character
            }

            if (value > prevValue) {
                // Check for subtractive combinations
                if ((value == 5 * prevValue || value == 10 * prevValue) && repetitionCount == 1) {
                    // Subtractive combinations like IV, IX, XL, XC, CD, CM
                    result += value - 2 * prevValue;
                    repetitionCount = 0;
                } else {
                    return false; // Invalid subtractive combination
                }
            } else {
                // Reset repetition count for non-subtractive combinations
                repetitionCount = (value == prevValue) ? repetitionCount + 1 : 1;

                if (repetitionCount > 3) {
                    return false; // Invalid repetition
                }
            }

            prevValue = value;
        }

        return true;
    }

    public static void main(String[] args) {
        String romanNumeral = "XCXIV";

        if (isValidRomanNumeral(romanNumeral)) {
            int result = romanToInt(romanNumeral);
            System.out.println("The integer representation of " + romanNumeral + " is: " + result);
        } else {
            System.out.println("Invalid Roman numeral input.");
        }
    }
}
