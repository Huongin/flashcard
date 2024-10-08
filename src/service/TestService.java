package service;

import constant.Status;
import entity.Card;
import entity.Deck;
import entity.Test;
import entity.User;
import main.Main;
import util.FileUtil;
import util.InputUtil;

import java.util.*;

public class TestService {
    private final FileUtil<Test> fileUtil = new FileUtil<>();
    private static final String TEST_DATA_FILE = "test.json";
    private static int AUTO_ID;
    private List<Test> tests;

    private final UserService userService;
    private final DeckService deckService;
    private final CardService cardService;

    public TestService(UserService userService, DeckService deckService, CardService cardService) {
        this.userService = userService;
        this.deckService = deckService;
        this.cardService = cardService;
    }

    public void setTests() {
        List<Test> testList = fileUtil.readDataFromFile(TEST_DATA_FILE, Test[].class);
        tests = testList != null ? testList : new ArrayList<>();
    }

    public void saveTestsData() {
        fileUtil.writeDataToFile(tests, TEST_DATA_FILE);
    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (Test test : tests) {
            if (test.getId() > maxId) {
                maxId = test.getId();
            }
        }
        AUTO_ID = maxId+1;
    }


    public void createNewTest() {
        System.out.println("Nhập tên bài test cần tạo: ");
        String name = new Scanner(System.in).nextLine();

        //Lấy danh sách thẻ trong bộ thẻ đã chọn
        List<Card> cardsInSelectedDeck = selectDeckAndGetCards();
        if (cardsInSelectedDeck == null || cardsInSelectedDeck.isEmpty()) {
            System.out.println("Không có thẻ nào trong bộ thẻ đã chọn");
            return;
        }
        //Tạo bài kiểm tra
        Test test = new Test(AUTO_ID++);
        test.setName(name);// sử dụng tên nhập ban đầu
        System.out.println("Chọn trạng thái cho bài test");
        System.out.println("1. Cho phép làm test");
        System.out.println("2. Không cho phép làm test");
        int statuschoose = InputUtil.chooseOption("Xin mời chọn chức năng " ,
                "Chức năng là số dương từ 1 tới 2, vui lòng nhập lại: ", 1, 2);
        switch (statuschoose) {
            case 1:
                test.setTestStatus(Status.ACTIVE);
                break;
            case 2:
                test.setTestStatus(Status.INACTIVE);
                break;
        }
        test.setCreatedDate(new Date());
        test.setCard(cardsInSelectedDeck);

        // Tính ngưỡng điểm đạt dựa trên 60% tổng số từ trong bộ thẻ
        int maxScore = 10;// thang điểm 10
        int totalCards = cardsInSelectedDeck.size();

        int passScoreThresholdPersent;
        while (true) {
            System.out.println("Nhập phần trăm ngưỡng điểm đạt (0~100): ");
            passScoreThresholdPersent = new Scanner(System.in).nextInt();
            if (passScoreThresholdPersent < 0 || passScoreThresholdPersent > 100) {
                System.out.println("Giá trị không hợp lệ, vui lòng nhập lại số từ 0 đến 100");
            } else {
                break;
            }
        }

        int correctCardsThreshold = (int) Math.ceil(totalCards * (passScoreThresholdPersent / 100.0));//Tính số câu đúng cần đạt
        int passScoreThreshold =(correctCardsThreshold * maxScore) /totalCards;// Điểm cần đạt

        System.out.println("Số câu đúng cần đạt: " + correctCardsThreshold);
        System.out.println("Ngưỡng điểm đạt kiểm tra là: " + passScoreThreshold);

        test.setPassScoreThreshold(passScoreThreshold);


        tests.add(test);
        saveTestsData();
    }

    private List<Card> selectDeckAndGetCards() {
        // Lấy danh sách bộ thẻ của tài khoản đăng nhập
        List<Deck> testDecks = deckService.getUserCreatedDecks(Main.LOGGED_IN_USER);
        if (testDecks.isEmpty()) {
            System.out.println("Không có bộ thẻ nào trong danh sách bộ thẻ của admin");
            return null;
        }
        //Hiển thị danh sách bộ thẻ

        System.out.println("Danh sách bộ thẻ của bạn: ");
        for (Deck deck : testDecks) {
            System.out.println("ID: " + deck.getId() + ", Chủ đề: " + deck.getTopic() + ", Level: " + deck.getLevel());
        }
        //Chọn bộ thẻ cho bài kiểm tra
        System.out.print("Mời bạn nhập ID bộ thẻ: ");
        int deckId = new Scanner(System.in).nextInt();
        Deck selectedDeck = null;

        for (Deck deck : testDecks) {
            if (deck.getId() == deckId) {
                selectedDeck = deck;
                break;
            }
        }
        if (selectedDeck == null) {
            System.out.println("Không tìm thấy bộ thẻ với ID đã nhập.");
            return null;
        }
        List<Card> cardsInSelectedDeck = cardService.findCardsByDeckId(deckId);
        if (cardsInSelectedDeck == null || cardsInSelectedDeck.isEmpty()) {
            System.out.println("Không có thẻ nào trong bộ thẻ: " + selectedDeck.getId());
            return null;
        }
        return cardsInSelectedDeck;
    }

    public void updateTestById() {
        while (true) {
            System.out.println("Mời bạn nhập ID của bài kiểm tra: ");
            int testId;
            while (true) {
                try {
                    testId = new Scanner(System.in).nextInt();
                    break;// Thoát khỏi vòng lặp nếu giá trị nhập vào là số nguyên
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là số nguyên");
                }
            }
            Test test = findTestById(testId);
            if (test == null) {
                System.out.println("Thông tin chưa chính xác vui lòng nhập lại.");
                continue;
            }
            if (test.getTestStatus() == Status.INACTIVE) {
                System.out.println("Bài kiểm tra đang bị khóa, Vui lòng thay đổi trạng thái trước khi cập nhật thông tin");
                System.out.println("1. Thay đổi trạng thái bài kiểm tra");
                System.out.println("2. Thoát");
                int option = InputUtil.chooseOption("Xin mời chọn chức năng " ,
                        "Chức năng là số dương từ 1 tới 2, vui lòng nhập lại: ", 1, 2);
                if (option == 1) {
                    System.out.println("Chọn lại trạng thái cho bài test");
                    System.out.println("1.Chuyển thành kích hoạt (ACTIVE)");
                    System.out.println("2.Thoát (Giữ nguyên trạng thái khóa (INACTIVE))");
                    int newStatus = InputUtil.chooseOption("Xin mời chọn chức năng " ,
                            "Chức năng là số dương từ 1 tới 2, vui lòng nhập lại: ", 1, 2);
                    switch (newStatus) {
                        case 1:
                            test.setTestStatus(Status.ACTIVE);
                            System.out.println("Trạng thái bài test đã kích hoạt");
                            break;
                        case 2:
                           return;
                    }
                }
                return;
            }
            System.out.println("Mời bạn chọn phần thông tin muốn cập nhật lại: ");
            System.out.println("1. Tên bài test");
            System.out.println("2. Ngày tạo bài test");
            System.out.println("3. Ngưỡng điểm bài test");
            System.out.println("4. Thoát");
            int choose = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1, 4);
            switch (choose) {
                case 1:
                    System.out.println("Nhập tên mới cho bài test : ");
                    String newName = new Scanner(System.in).nextLine();
                    test.setName(newName);
                    break;
                case 2:
                    test.setCreatedDate(new Date());//Cập nhật ngày tạo là ngày hiện tại
                    break;
                case 3:
                    //Lấy danh sách thẻ trong bộ thẻ đã chọn
                    List<Card> cardsInselectedDeck = test.getCard();
                    if (cardsInselectedDeck.isEmpty()) {
                        System.out.println("Không có thẻ nào trong bộ thẻ đã chọn");
                        break;
                    }
                    System.out.println("Nhập ngưỡng điểm đạt mới (tính theo %): ");
                    int totalCards = cardsInselectedDeck.size();
                    int passScoreThresholdPersent = new Scanner(System.in).nextInt();
                    if (passScoreThresholdPersent < 0 || passScoreThresholdPersent > 100) {
                        System.out.println("Giá trị không hợp lệ, vui lòng nhập lại số từ 0 đến 100");
                    } else {
                        int passScoreThreshold = (int) Math.ceil(totalCards * (passScoreThresholdPersent / 100.0));// Điểm được làm tròn lên
                        System.out.println("Ngưỡng điểm đạt kiểm tra là: " + passScoreThreshold);

                        test.setPassScoreThreshold(passScoreThreshold);
                    }
                    break;
                case 4:
                    return;
            }
            System.out.println("ID: " + test.getId() + ", Name: " + test.getName() + ", TestStatus: " + test.getTestStatus() + ", CreatedDate: " + test.getCreatedDate() + ", PassScoreThreshold: " + test.getPassScoreThreshold());
            saveTestsData();
        }
    }

    public Test findTestById(int idTest) {
        for (Test test : tests) {
            if (test.getId() == idTest) {
                return test;
            }
        }
        return null;
    }

    public void showTestList() {
        printHeader();
        if (tests == null || tests.isEmpty()) {
            System.out.println("Hiện tại chưa có bài kiểm tra nào");
            return;
        }
        for (Test test : tests) {
            showTestDetail(test);
        }
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-25s%-30s%-10s%n", "ID", "Name", "TestStatus", "CreatedDate", "PassScoreThreshold");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showTestDetail(Test test) {
        System.out.printf("%-5s%-30s%-25s%-30s%-10s%n", test.getId(), test.getName(), test.getTestStatus(), test.getCreatedDate(), test.getPassScoreThreshold());
    }
}
