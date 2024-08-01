package service;

import entity.Card;

import java.util.Scanner;

public class CardService {
    public void createCard() {
        Card card = new Card();
        System.out.println("Mời bạn nhập từ vựng: ");
        card.setWord(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập phiên âm của từ: ");
        card.setPhonetic(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn chọn loại từ vựng");


        
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
