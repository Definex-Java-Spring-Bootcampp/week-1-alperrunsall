package com.patika.kredinbizdenservice;

import com.patika.kredinbizdenservice.enums.ApplicationStatus;
import com.patika.kredinbizdenservice.enums.SectorType;
import com.patika.kredinbizdenservice.model.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String[] names = new String[]{"Alper", "Alper2", "Alper3", "Alper4", "Alper5", "Alper6", "Alper7", "Alper8", "Alper9", "Alper10", "Alper11", "Alper12"};

        int userCount = 10;
        // Belirtilen sayıda rastgele kullanıcı oluştur
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.registerUser("Cem", "Cem", getRandomBirthDate(),
                "cemdrman@gmail.com", "şifre", "+9000000000", true);
        for (int i = 0; i < userCount; i++) {
            Random random = new Random();
            userDatabase.registerUser(names[random.nextInt(names.length)], "surname",
                    getRandomBirthDate(), "user" + i + "@gmail.com",
                    "şifre", "+90536000000", true);
        }

        Loan loan1 = new VehicleLoan(new BigDecimal("50000"), 60, 0.5);
        Loan loan2 = new VehicleLoan(new BigDecimal("70000"), 48, 0.7);
        Loan loan3 = new VehicleLoan(new BigDecimal("100000"), 72, 0.8);
        Loan loan4 = new HouseLoan(new BigDecimal("250000"), 72, 0.7);
        Loan loan5 = new HouseLoan(new BigDecimal("100000"), 36, 0.4);
        Loan loan6 = new HouseLoan(new BigDecimal("200000"), 48, 0.5);
        Loan loan7 = new ConsumerLoan(new BigDecimal("20000"), 12, 0.6);
        Loan loan8 = new ConsumerLoan(new BigDecimal("10000"), 6, 0.3);
        Loan loan9 = new ConsumerLoan(new BigDecimal("5000"), 3, 0.2);

        List<Loan> loanList = List.of(loan1, loan2, loan3, loan4, loan5, loan6, loan7, loan8, loan9);

        Campaign campaign1 = new Campaign("Yaz İndirimi", "Yaz sezonunda geçerli indirimler", LocalDate.of(2024, 6, 30), LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10), SectorType.TRAVELS);
        Campaign campaign2 = new Campaign("Emlak Kredisi Kampanyası", "Ev almak isteyenlere özel faiz oranları", LocalDate.of(2024, 12, 31), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15), SectorType.FINANCE);
        Campaign campaign3 = new Campaign("Yılbaşı Özel", "Yılbaşına özel süpermarketlerde kaçırılmayacak fırsatlar", LocalDate.of(2024, 12, 31), LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 15), SectorType.MARKET);


        CreditCard creditCard1 = new CreditCard(BigDecimal.valueOf(159L), List.of(campaign1));
        CreditCard creditCard2 = new CreditCard(BigDecimal.valueOf(99L), List.of(campaign1, campaign3));
        CreditCard creditCard3 = new CreditCard(BigDecimal.valueOf(0L), List.of(campaign1, campaign2, campaign3));

        List<CreditCard> creditCardList = List.of(creditCard1, creditCard2, creditCard3);

        /*Bank bank1 = new Bank("Banka1", List.of(loan1, loan4, loan6, loan9), List.of(creditCard1));
        Bank bank2 = new Bank("Banka2", List.of(loan1, loan2), List.of(creditCard2));
        Bank bank3 = new Bank("Banka3", List.of(loan3, loan5, loan7, loan8), List.of(creditCard3));
        List<Bank> bankList = List.of(bank1,bank2,bank3);*/

        Random random = new Random();
        List<Application> applicationList = new ArrayList<>();

        int applicationCount = random.nextInt(20, 30);
        for (int i = 0; i < applicationCount; i++) {
            User user = userDatabase.randomUser();
            Loan loan = loanList.get(random.nextInt(loanList.size()));
            CreditCard creditCard = creditCardList.get(random.nextInt(creditCardList.size()));
            Application application = new Application();
            application.setLoan(loan);
            application.setCreditCard(creditCard);
            application.setUser(user);
            application.setLocalDateTime(getRandomLocalDateTime());
            application.setApplicationStatus(ApplicationStatus.IN_PROGRESS);
            applicationList.add(application);
            user.getApplicationList().add(application);
        }

        List<User> allUsers = userDatabase.getAllUsers();
        User maxApplicationUser = allUsers.get(0);

        for (int i = 1; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getApplicationList().size() > maxApplicationUser.getApplicationList().size()) {
                maxApplicationUser = user;
            }
        }
        System.out.println("En cok basvuru yapan kullanici " + maxApplicationUser.getName());

        Application maxApplication = applicationList.get(0);
        for (int i = 1; i < applicationList.size(); i++) {
            Application application = applicationList.get(i);
            if (application.getLoan().getAmount().compareTo(maxApplication.getLoan().getAmount()) > 0) {
                maxApplication = application;
            }
        }
        System.out.println("En yuksek kredi isteyen kullanici " + maxApplication.getUser().getName() + " istedigi tutar " + maxApplication.getLoan().getAmount());


        LocalDateTime lastMonths = LocalDateTime.now().minusMonths(1L);
        applicationList.stream()
                .filter(application -> application.getLocalDateTime().isAfter(lastMonths))
                .forEach(app -> System.out.println(app.getLocalDateTime()));

        creditCardList.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getCampaignList().size(), o1.getCampaignList().size()))
                .forEach(sort -> System.out.println(sort.getFee() +  " ucretli kartin kampanya sayisi: " + sort.getCampaignList().size()));

        String listApplicationsByEmail = "cemdrman@gmail.com";
        System.out.println(listApplicationsByEmail + " Maili ile " +
                findApplicationsByEmail(applicationList,listApplicationsByEmail).size() + " adet basvuru bulunuyor ve liste olarak tutuluyor");

    }

    public static List<Application> findApplicationsByEmail(List<Application> list, String email) {
        return list.stream().filter(item -> item.getUser().getEmail().equals(email))
                .toList();
    }

    public static LocalDate getRandomBirthDate() {
        Random random = new Random();
        return LocalDate.now().minusYears(random.nextInt(50) + 18).minusMonths(random.nextInt(11)).minusDays(random.nextInt(30));
    }

    public static LocalDateTime getRandomLocalDateTime() {
        Random random = new Random();
        return LocalDateTime.now().minusDays(random.nextInt(90));
    }
}
