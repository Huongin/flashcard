package service;

import Main.Main;
import constant.CardType;
import constant.State;
import constant.UserRole;
import entity.Card;
import entity.Deck;
import entity.User;
import util.InputUtil;

import java.util.Scanner;

import static constant.CardType.Noun;
public class CardService {
    private  User currentUser; //Người dùng hiện tại
    private  Deck deck; //Deck duy nhất lưu trữ thẻ

    public void createCard() {
        Card card = new Card();
        System.out.println("Mời bạn nhập từ vựng: "); //Nhập từ vựng
        card.setWord(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập phiên âm của từ: ");// Nhập phiên âm
        card.setPhonetic(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn chọn loại từ vựng");// Nhập loại từ
        System.out.println("1. Danh từ");
        System.out.println("2. Tính từ i");
        System.out.println("3. Tính từ na");
        System.out.println("4. Động từ");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 4, Vui lòng nhập lại: ", 1, 5);
        switch (choice) {
            case 1:
                card.setCardType(CardType.Noun);
                break;
            case 2:
                card.setCardType(CardType.Adji);
                break;
            case 3:
                card.setCardType(CardType.AdjNa);
                break;
            case 4:
                card.setCardType(CardType.Verb);
                break;
        }
        System.out.println("Mời bạn chọn quyền sử dụng thẻ học: ");//Chọn trạng thái của thẻ
        System.out.println("1.Được phép học");
        System.out.println("2.Chưa được phép học");
        int cardState = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 2, Vui lòng nhập lại: ", 1, 2);
        switch (cardState) {
            case 1:
                card.setState(State.Active);
                break;
            case 2:
                card.setState(State.INACTIVE);
                break;
        }
        System.out.println("Nhập ví dụ về cách dùng từ"); //Nhập ví dụ
        card.setExample(new Scanner(System.in).nextLine());
        System.out.println("Khai báo người tạo thẻ"); // Chọn người tạo thẻ
        System.out.println("Nhập ID của người tạo thẻ: ");
        int creatorId = new Scanner(System.in).nextInt();
        User creator = UserService.findUserById(creatorId);
        if (creator == null) {
            System.out.println("Người dùng không tồn tại.");
            return;
        }
        //Cài đặt người tạo thẻ
        card.setCreator(Main.LOGGED_IN_USER);
        if (Main.LOGGED_IN_USER.getRole() == UserRole.ADMIN) {
            card.setShared(true);
        } else {
            card.setShared(false);
        }
        //Lưu thẻ vào deck

    }


    public void updateCardInfo() {
    }

    public void searchCardByWord() {
    }

    public void searchCardByTopic() {
    }

    public void listOfFlashcards() {
    }
}
