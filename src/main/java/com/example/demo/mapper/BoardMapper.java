package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	@Select("""
			SELECT
				id,
				title,
				writer,
				inserted
			FROM Board
			ORDER BY id DESC;
			""")
	List<Board> selectAll();

	@Select("""
			SELECT 
			 	b.id,
			 	b.title,
			 	b.body,
			 	b.writer,
			 	b.inserted,
			 	f.fileName
			FROM Board b LEFT JOIN FileNames f ON b.id = f.boardId
			WHERE b.id = #{id}
			""")
	@ResultMap("boardResultMap")
	Board selectById(Integer id);

	@Update("""
			UPDATE Board
			SET
				title = #{title},
				body = #{body},
				writer = #{writer}
			WHERE 
				id = #{id}
			""")
	int update(Board board);

	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);
	
	@Insert("""
			INSERT INTO 
			Board (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int add(Board board);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'"/>
			
			SELECT
				id,
				title,
				writer,
				inserted
			FROM Board
			<where> 
				<if test="type eq 'title' or type eq 'all'">
				   title  LIKE #{pattern}
				</if>
				<if test="type eq 'body' or type eq 'all'">
					OR body   LIKE #{pattern}
				</if>
				<if test="type eq 'writer' or type eq 'all'">
					OR writer LIKE #{pattern}
				</if>
			</where>
			ORDER BY id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'"/>
			SELECT COUNT(*) FROM Board
			<where>
				<if test="type eq 'title' or type eq 'all'">
				   title  LIKE #{pattern}
				</if>
				<if test="type eq 'body' or type eq 'all'">
					OR body   LIKE #{pattern}
				</if>
				<if test="type eq 'writer' or type eq 'all'">
					OR writer LIKE #{pattern}
				</if>
			</where>
			</script>
			""")
	Integer countAll(String search, String type);

	@Insert("""
			INSERT INTO FileNames (boardId, fileName)
			VALUES(#{boardId}, #{Filename})
			""")
	int insertFileName(Integer boardId, String Filename);

	
	
	
	
		
	
}
