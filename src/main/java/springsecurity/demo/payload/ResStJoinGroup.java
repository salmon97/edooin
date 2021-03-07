package springsecurity.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResStJoinGroup {
    private String joinedDate;
    private String studentFullName;
    private String groupName;
    private int lessonCount;
    private double balance;
    private double price;
    private Date expiredBalance;

    public ResStJoinGroup(String joinedDate, int lessonCount, double balance, Date expiredBalance, double price) {
        this.joinedDate = joinedDate;
        this.lessonCount = lessonCount;
        this.balance = balance;
        this.expiredBalance = expiredBalance;
        this.price = price;
    }


    public ResStJoinGroup(String joinedDate, String groupName, double balance) {
        this.joinedDate = joinedDate;
        this.groupName = groupName;
        this.balance = balance;
    }
}
