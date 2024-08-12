package service;

import constant.Status;
import entity.Card;
import entity.Test;
import entity.TestDetail;
import entity.User;
import util.FileUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TestDetailService {

    private final FileUtil<TestDetail> fileUtil = new FileUtil<>();
    private static final String TEST_DETAIL_DATA_FILE = "testdetails.json";
    private List<TestDetail> testDetails;
    private final UserService userService;
    private final DeckService deckService;
    private final CardService cardService;
    private final TestService testService;

    public TestDetailService(UserService userService,DeckService deckService, CardService cardService,TestService testService){
        this.userService = userService;
        this.deckService = deckService;
        this.cardService = cardService;
        this.testService = testService;
    }
    public void setTestDetails(){
        List<TestDetail> testDetailList = fileUtil.readDataFromFile(TEST_DETAIL_DATA_FILE, TestDetail[].class);
        testDetails = testDetailList != null ? testDetailList : new ArrayList<>();
    }
    public void saveTestDetailData(){
        fileUtil.writeDataToFile(testDetails,TEST_DETAIL_DATA_FILE);
    }
    public void takeTest() {
        User user = userService.getLoggedInUser();
        TestService testService = new TestService(userService,deckService,cardService);
        testService.setTests();//Đọc dữ liệu bài kiểm tra từ File
        testService.showTestList();//Hiển thị danh sách bài kiểm tra
        System.out.println("Mời bạn nhập ID của bài kiểm tra muốn làm: ");
        int testId = new Scanner(System.in).nextInt();
        Test test = testService.findTestById(testId);
        if(test == null || test.getTestStatus() == Status.INACTIVE){
            System.out.println("Bài kiểm tra không tồn tại hoặc đang trạng thái khóa");
            return;
        }
        List<Card> cards = test.getCard();
        if(cards == null || cards.isEmpty()) {
            System.out.println("Bài kiểm tra không có thẻ học để làm test.");
            return;
        }
        List<Card> correctCards = new ArrayList<>();
        List<Card> incorrectCards =new ArrayList<>();
        int score = 0;
        for (Card card : cards){
            System.out.println("Nghĩa của từ: " + card.getMeaning());
            System.out.println("Nhập từ vựng tương ứng với nghĩa trên: ");
            String userAnswer = new Scanner(System.in).nextLine();
            if (card.getWord().equalsIgnoreCase(userAnswer) || card.getPhonetic().equalsIgnoreCase(userAnswer)) {
                correctCards.add(card);
                score++;
            }else {
                incorrectCards.add(card);
            }
        }
        int passScoreThreshold = test.getPassScoreThreshold();
        boolean isPassed = score >= passScoreThreshold;
        String result = isPassed? "Có" : "Không";
        System.out.printf("Có vượt qua bài test không? : ", result);
        System.out.printf("Điểm đạt được: ",score,cards.size());
        System.out.printf("Số từ đúng: ", correctCards.size());
        System.out.printf("Số từ sai: ",incorrectCards.size());

        //Lưu kết quả test
        TestDetail testDetail = new TestDetail(user,test,new Date(), correctCards,incorrectCards,score);
        testDetails.add(testDetail);
        saveTestDetailData();

        //Kết quả test
        System.out.println("Tổng điểm: " + score + "Số từ đúng: " + correctCards.size() + "Số từ sai: " + incorrectCards.size() + "Điểm đạt yêu cầu: " + (isPassed ? "Có" : "Không"));

    }


    //Lấy về danh sách các TestDetail của một người dùng
    public List<TestDetail> TestDetailsByUser(User user){
        List<TestDetail> userTestDetail = new ArrayList<>();
        for (TestDetail testDetail : testDetails) {
            if (testDetail.getUser().equals(user)) {
                userTestDetail.add(testDetail);
            }
        }
        return userTestDetail;
    }

    public void showTestDetailList() {
        printHeader();
        if(testDetails == null || testDetails.isEmpty()){
            System.out.println("Hiện tại chưa có bài kiểm tra nào");
            return;
        }
        for (TestDetail testDetail : testDetails){
            showTestDetail(testDetail);
        }
    }

    public void printHeader() {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "User","Test", "DateTest", "CorrectCards", "IncorrectCards", "Score");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showTestDetail(TestDetail testDetail) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n",testDetail.getUser(), testDetail.getTest(), testDetail.getTestDate(), testDetail.getCorrectCard(), testDetail.getIncorrectCard(), testDetail.getScore() );
    }

    public void testResultsByIdTest(int testId) {
        boolean found = false;
        for (TestDetail testDetail : testDetails){
            if (testDetail.getTest().getId() == testId){
                System.out.println("Kết quả kiểm tra cho bài test ID " + testId + ":" );
                showTestDetailList();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy kết quả cho bài test ID " + testId + ":");
        }
    }

    public void testResultsByUserId(int userId) {
        boolean found = false;
        for (TestDetail testDetail : testDetails){
            if (testDetail.getUser().getId() == userId){
                System.out.println("Kết quả kiểm tra cho bài test ID " + userId + ":" );
                showTestDetailList();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy kết quả cho bài test ID " + userId+ ":");
        }
    }
}
