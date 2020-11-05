package course.springdata.advanced.util;

import course.springdata.advanced.entity.Shampoo;

public class PrintUtil {
    public static void printShampoo(Shampoo s) {
            System.out.format("|%5d | %-30.30s | %-8.8s | %8.2f | %-40.40s |%n",
            s.getId(), s.getBrand(), s.getSize(), s.getPrice(),
            s.getLabel().getTitle() + " - " + s.getLabel().getSubtitle());
    }
}
