package com.practice.blog.comment.service;

import com.practice.blog.account.dto.response.AccountCommentResponse;
import com.practice.blog.account.entity.Account;
import com.practice.blog.account.service.AccountsService;
import com.practice.blog.comment.domain.Comment;
import com.practice.blog.comment.dto.request.CommentRequest;
import com.practice.blog.comment.repository.CommentRepository;
import com.practice.blog.post.domain.Post;
import com.practice.blog.post.dto.response.PostCommentResponse;
import com.practice.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final AccountsService accountsService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    //댓글 생성
    @Transactional
    public Long createComment(Long postId, CommentRequest commentRequest){
        Long accountId = commentRequest.getAccountId();
        Account writer = accountsService.findByAccountId(accountId);
        Post post = postService.findByPostId(postId);
        Comment newComment = commentRequest.toEntity(writer, post);

        commentRepository.save(newComment);

        return newComment.getId();
    }

    //postId로 댓글 목록 조회
    @Transactional(readOnly = true)
    public PostCommentResponse getPostCommentList(Long postId){
        List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAt(postId);
        return PostCommentResponse.of(postId, commentList);
    }

    //accountId로 댓글 목록 조회
    @Transactional(readOnly = true)
    public AccountCommentResponse getAccountCommentList(Long accountId){
        Account account = accountsService.findByAccountId(accountId);
        List<Comment> commentList = commentRepository.findAllByWriterAccountIdOrderByCreatedAtDesc(accountId);
        return AccountCommentResponse.of(account, commentList);
    }


}
