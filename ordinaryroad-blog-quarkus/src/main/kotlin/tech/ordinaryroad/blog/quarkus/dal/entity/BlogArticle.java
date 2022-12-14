/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.blog.quarkus.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.ibatis.type.JdbcType;
import tech.ordinaryroad.blog.quarkus.enums.BlogArticleStatus;
import tech.ordinaryroad.commons.mybatis.quarkus.type.StringListTypeHandler;

import java.util.List;

@TableName(value = "blog_article", autoResultMap = true)
@RegisterForReflection
public class BlogArticle extends BaseBlogDO {

    private static final long serialVersionUID = -8948779674195993743L;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面地址
     */
    private String coverImage;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;

    /**
     * 文章状态
     */
    private BlogArticleStatus status;

    /**
     * 是否允许评论
     */
    private Boolean canComment;

    /**
     * 是否允许打赏
     */
    private Boolean canReward;

    /**
     * 是否为原创，原创时加转载声明
     */
    private Boolean original;

    /**
     * 最开始的草稿Id
     */
    private String firstId;

    /**
     * 分类Id
     */
    private String typeId;

    /**
     * 标签Id列表
     */
    @TableField(
            jdbcType = JdbcType.BLOB,
            typeHandler = StringListTypeHandler.class
    )
    private List<String> tagIds;

    public BlogArticle() {
    }

    public BlogArticle(String title, String coverImage, String summary, String content, BlogArticleStatus status, Boolean canComment, Boolean canReward, Boolean original, String firstId, String typeId, List<String> tagIds) {
        this.title = title;
        this.coverImage = coverImage;
        this.summary = summary;
        this.content = content;
        this.status = status;
        this.canComment = canComment;
        this.canReward = canReward;
        this.original = original;
        this.firstId = firstId;
        this.typeId = typeId;
        this.tagIds = tagIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogArticleStatus getStatus() {
        return status;
    }

    public void setStatus(BlogArticleStatus status) {
        this.status = status;
    }

    public Boolean getCanComment() {
        return canComment;
    }

    public void setCanComment(Boolean canComment) {
        this.canComment = canComment;
    }

    public Boolean getCanReward() {
        return canReward;
    }

    public void setCanReward(Boolean canReward) {
        this.canReward = canReward;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }
}
