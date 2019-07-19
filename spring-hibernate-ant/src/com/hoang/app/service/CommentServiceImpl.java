package com.hoang.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Hoang Truong
 */

@Service("commentService")
@Transactional(propagation=Propagation.MANDATORY)
public class CommentServiceImpl implements CommentService  {

//    @Autowired
//    private CommentDao commentDao;
//    
//    public Comment getComment(String key, String cf) {
//        if (key == null || cf == null) {
//            return null;
//        }
//        return commentDao.read(key, cf);
//    }
//
//    public boolean hasComment(String key, String cf) {
//        return getComment(key, cf) != null;
//    }
//      
//    public void save(Comment comment) {
//        commentDao.save(comment);
//    }
//    
//    public void delete(Comment comment) {
//        commentDao.delete(comment);
//    }
//
//
//    // Finding
//    public List<Comment> getComments(String key, String cf) {
//        return commentDao.findAll(key, cf);
//    }    
}
