package com.wittypuppy.backend.mainpage.entity;

import com.wittypuppy.backend.board.entity.BoardMember;
import com.wittypuppy.backend.common.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "MAINPAGE_BOARD")
@Table(name = "tbl_board")
public class MainPageBoard {
    @Id
    @Column(name = "board_code",columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCode;

    @Column(name = "board_manager_code",columnDefinition = "BIGINT")
    private Long boardManagerCode;

    @Column(name = "board_group_code",columnDefinition = "BIGINT")
    private Long boardGroupCode;

    @Column(name = "board_title",columnDefinition = "VARCHAR(500)")
    private String boardTitle;

    @Column(name = "board_description",columnDefinition = "VARCHAR(3000)")
    private String boardDescription;

    @Column(name = "board_access_status",columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String boardAccessStatus;

    @JoinColumn(name = "board_code")
    @OneToMany
    private List<MainPagePost> postList;

    @JoinColumn(name = "board_code")
    @OneToMany
    private List<MainPageBoardMember> boardMemberList;
}
