package taba.team4.eut.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
//public class PageEntity<Dto extends BaseEntity> {
public class PageEntity<Dto> {

    private int pageNo = 1;
    private int pagePerCnt = 10;
    private int pageGroupPerCnt = 10;

    private Dto searchDto;

    private String searchKey;
    private String searchValue;

    private int startRow;
    private int endRow;

    private long totalCnt;
    private List<Dto> dtoList;

    /**
     * 검색 쿼리 조건을 key:value 맵 형태로 전달 한다.
     *
     * 	ex)
     *  -- java 파일
     *  Map<String, String> searchMap = new Map<String, String>();
     *  searchMap.put("title", "검색내용");
     *
     *  -- mybaitis 파일
     *  <if test='searchMap != null '>
     *  	<if test='searchMap.title != null and !searchMap.title.equals("")'>
     *  		AND TITLE = #{searchMap.title}
     *  	</if>
     *  </if>
     */
    private Map<String, String> searchMap;

    /**
     * 검색 쿼리 조건을 key:value 맵 형태로 전달 한다.
     * Query 문에서는 Like 'xxx%' 검색을 한다.
     *
     * 	ex)
     *  -- java 파일
     *  Map<String, String> rightLikeSearchMap = new Map<String, String>();
     *  rightLikeSearchMap.put("title", "검색내용");
     *
     *  -- mybaitis 파일
     *  <if test='rightLikeSearchMap != null '>
     *  	<if test='rightLikeSearchMap.ordpeNm != null and !rightLikeSearchMap.ordpeNm.equals("")'>
     *  		AND ORDPE_NM LIKE CONCAT(#{rightLikeSearchMap.ordpeNm},'%')
     *  	</if>
     *  </if>
     */
    private Map<String, String> rightLikeSearchMap;

    /**
     * 검색 쿼리 조건을 key:value 맵 형태로 전달 한다.
     * Query 문에서는 Like '%xxx' 검색을 한다.
     *
     * 	ex)
     *  -- java 파일
     *  Map<String, String> leftLikeSearchMap = new Map<String, String>();
     *  leftLikeSearchMap.put("title", "검색내용");
     *
     *  -- mybaitis 파일
     *  <if test='leftLikeSearchMap != null '>
     *  	<if test='leftLikeSearchMap.ordpeNm != null and !leftLikeSearchMap.ordpeNm.equals("")'>
     *  		AND ORDPE_NM LIKE CONCAT(#{leftLikeSearchMap.ordpeNm},'%')
     *  	</if>
     *  </if>
     */
    private Map<String, String> leftLikeSearchMap;

    /**
     * searchMap 사용시 value가 string이 아닌 object로 넘 길 때 사용
     *
     * ex)
     * -- java 파일
     * 		Map<String, Object> searchObjectMap = new HashMap<String, Object>();
     * 		ArrayList<String> itemIdsList = new ArrayList<String>();
     * 		itemIdsList.add("검색값");
     * 		searchObjectMap.put("itemIds", itemIdsList);
     *
     * -- mybatis 파일
     *		<choose>
     *			<when test="searchObjectMap.itemIds != null and searchObjectMap.itemIds.size != 0">
     *				AND GIT.ITEM_ID IN
     *				<foreach collection="searchObjectMap.itemIds" item="itemId" index="index" separator="," open="(" close=")">
     *					#{itemId}
     *				</foreach>
     *			</when>
     *		</choose>
     */
    private Map<String, Object> searchObjectMap;

    public PageEntity() { }

    public PageEntity(int pagePerCnt) {
        this.pagePerCnt = pagePerCnt;
    }

    public PageEntity(Pageable pageable) {
        this.pagePerCnt = pageable.getPageSize();
        this.pageNo = pageable.getPageNumber() + 1;
    }

    public PageEntity(int pagePerCnt, Map<String, String> searchMap) {
        this.pagePerCnt = pagePerCnt;
        this.searchMap = searchMap;
    }

    public PageEntity(int pagePerCnt, Dto searchDto) {
        this.pagePerCnt = pagePerCnt;
        this.searchDto = searchDto;
    }

    public PageEntity(int pagePerCnt, Dto searchDto, Map<String, String> searchMap) {
        this.pagePerCnt = pagePerCnt;
        this.searchMap = searchMap;
        this.searchDto = searchDto;
    }

    /**
     * Mysql Limit 쿼리문의 startRow 번호 리턴
     * @return
     */
    public int getStartRow() {
        setStartRow( (this.pageNo-1) * this.pagePerCnt );
        return this.startRow;
    }

    /**
     * Mysql Limit 쿼리문의 startRow 번호 설정
     * @param startRow
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * Mysql Limit 쿼리문의 endRow 번호 리턴
     * @return
     */
    public int getEndRow() {
        setEndRow(this.pagePerCnt);
        return this.endRow;
    }

    /**
     * Mysql Limit 쿼리문의 endRow 번호 설정
     * @param endRow
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     * 데이터가 더 있는지 여부 리턴
     * @return
     */
    public boolean hasMore() {
        return (this.totalCnt > this.pageNo * this.pagePerCnt);
    }

    /**
     * 이전 페이지 번호 리턴
     * @return
     */
    public int getPrePageNo() {
        return (this.pageNo == 1) ? 1 : this.pageNo - 1;
    }

    /**
     * 다음 페이지 번호 리턴
     * @return
     */
    public int getNextPageNo () {
        return this.pageNo + 1;
    }

    /**
     * 현재 페이지 그룹 중 첫번째 페이지 번호 리턴
     * 	 ex) 21 | 22 | ..... | 30  => 21 리턴
     * @return
     */
    public int getCurGroupFirstPageNo () {
        int curGroupNo = (this.pageNo / this.pageGroupPerCnt);
        if ( (this.pageNo % this.pageGroupPerCnt) != 0 ) curGroupNo = curGroupNo + 1;

        return (curGroupNo-1) * this.pageGroupPerCnt + 1;
    }

    /**
     * 이전 그룹 페이지 번호 리턴
     *   ex) 21 | 22 | ..... | 30  => 11 리턴
     * @return
     */
    public int getPreGroupPageNo() {
        if (this.getCurGroupFirstPageNo() <= this.pageGroupPerCnt) return 1;
        return this.getCurGroupFirstPageNo() - this.pageGroupPerCnt;
    }

    /**
     * 다음 그룹 페이지 번호 리턴
     *   ex) 21 | 22 | ..... | 30  => 31 리턴
     * @return
     */
    public int getNextGroupPageNo() {
        if ( (this.getCurGroupFirstPageNo() + this.pageGroupPerCnt) >= this.getLastPageNo() ) {
            return this.getLastPageNo();
        }
        else {
            return this.getCurGroupFirstPageNo() + this.pageGroupPerCnt;
        }
    }

    /**
     * 마지막 페이지 번호 리턴
     * @return
     */
    public int getLastPageNo () {
        int lastPageNo = (int) (this.totalCnt / this.pagePerCnt);
        if ( (this.totalCnt % this.pagePerCnt) != 0 ) {
            lastPageNo = lastPageNo + 1;
        }
        return lastPageNo;
    }

    public void setSearchDto(final Dto searchDto) {
        this.searchDto = searchDto;
    }
}
