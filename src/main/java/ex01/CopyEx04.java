package ex01;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글을 나타내는 데이터 모델 클래스.
 */
@Data
class Board{
    private Integer id;
    private String title;
    private String content;
    private List<Reply> replies = new ArrayList<>(); // 게시글에 달린 댓글 목록
}

/**
 * 댓글을 나타내는 데이터 모델 클래스.
 */
@Data
class Reply{
    private Integer id;
    private String comment;

    public Reply(Integer id, String comment) {
        this.id = id;
        this.comment = comment;
    }
}

/**
 * 게시글 상세보기를 위한 데이터 전송 객체 (DTO).
 * Board 객체와 그에 속한 Reply 객체 목록을 조합하여 클라이언트에 필요한 형태로 가공된 데이터를 담습니다.
 */
@Data
class DetailDto{
    private Integer boardId;
    private String title;
    private String content;
    private List<String> comments = new ArrayList<>(); // 댓글 내용만 문자열 리스트로 저장

    /**
     * Board 객체를 인자로 받아 DetailDto를 생성하는 복사 생성자.
     * 이 과정에서 데이터의 형태가 변환됩니다 (객체 리스트 -> 문자열 리스트).
     * @param board 원본 데이터가 담긴 Board 객체
     */
    public DetailDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        // Java Stream API를 사용하여 '데이터 변환'을 수행합니다.
        // board.getReplies() : 댓글 객체(Reply)가 담긴 리스트를 가져옵니다.
        // .stream() : 리스트를 스트림으로 변환하여 파이프라인 연산을 시작합니다.
        // .map(Reply::getComment) : 스트림의 각 Reply 객체에 대해 getComment() 메서드를 호출하여,
        //                         스트림의 요소를 Reply 객체에서 String(댓글 내용)으로 변환합니다.
        // .toList() : 스트림의 모든 요소를 수집하여 새로운 List<String>으로 만듭니다.
        this.comments = board.getReplies().stream()
                .map(Reply::getComment)
                .limit(2)
                .toList();
    }
}

/**
 * 객체 복사 예제 4: 복잡한 객체(컬렉션 포함)를 DTO로 변환하기
 * 연관관계를 가진 객체(Board-Reply)를 단일 DTO(DetailDto)로 변환하는 방법을 보여줍니다.
 */
public class CopyEx04 {
    public static void main(String[] args) {
        // 1. 원본 데이터 생성
        // 게시글(Board) 객체 생성
        Board board = new Board();
        board.setId(1);
        board.setTitle("제목1");
        board.setContent("내용1");

        // 게시글에 추가할 댓글(Reply) 객체들 생성
        Reply reply1 = new Reply(1, "댓글1");
        Reply reply2 = new Reply(2, "댓글2");
        List<Reply> replies = new ArrayList<>();
        replies.add(reply1);
        replies.add(reply2);

        // 게시글에 댓글 리스트 추가
        board.setReplies(replies);

        // 2. 복사 생성자를 사용하여 Board 객체를 DetailDto로 변환
        DetailDto detailDto = new DetailDto(board);

        // 3. 변환된 DTO 객체 출력
        System.out.println(detailDto);
    }
}
