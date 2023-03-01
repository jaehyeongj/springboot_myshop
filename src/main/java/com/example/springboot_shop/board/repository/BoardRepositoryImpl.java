package com.example.springboot_shop.board.repository;//package com.nbcamp.gamematching.matchingservice.board.repository;
//
//import com.nbcamp.gamematching.matchingservice.board.entity.Board;
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import static com.nbcamp.gamematching.matchingservice.board.entity.QBoard.board;
//
//@Repository
//public class BoardRepositoryImpl extends QuerydslRepositorySupport {
//
//    private final JPAQueryFactory queryFactory;
//
//    public BoardRepositoryImpl(JPAQueryFactory queryFactory) {
//        super(Board.class);
//        this.queryFactory = queryFactory;
//    }
//
//
//
//        public Page<Board> pageList (String searchName, Pageable pageable){
//
//            QueryResults<Board> result = queryFactory
//                    .select(Projections.constructor(Board.class
//                    )).from(board)
//                    .where(search(searchName),search2(searchName))
//                    .fetchResults();
//            return new PageImpl<>(result.getResults(), pageable, result.getTotal());
//        }
//
//        private BooleanExpression search(String searchName){
//            return searchName != null ? board.nickname.contains(searchName) : null;
//        }
//
//    private BooleanExpression search2(String searchName){
//        return searchName != null ? board.content.contains(searchName) : null;
//    }
//
//}
