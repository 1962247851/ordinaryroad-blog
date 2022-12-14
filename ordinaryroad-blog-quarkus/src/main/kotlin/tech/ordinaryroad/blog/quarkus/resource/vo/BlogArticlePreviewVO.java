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

package tech.ordinaryroad.blog.quarkus.resource.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客文章预览VO类
 */
@JsonInclude
@JsonPropertyOrder
@RegisterForReflection
public class BlogArticlePreviewVO {

    private String uuid;

    private String coverImage;

    private String title;

    private String summary;

    private Boolean original;

    private BlogUserVO user;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;

    private String firstId;

    private BlogTypeVO type;

    private List<BlogTagVO> tags;

    private Long uv;

    private BigDecimal pv;

    private Long likesCount;

    private Long commentsCount;

    public BlogArticlePreviewVO() {
    }

    public BlogArticlePreviewVO(String uuid, String coverImage, String title, String summary, Boolean original, BlogUserVO user, LocalDateTime createdTime, LocalDateTime updateTime, String firstId, BlogTypeVO type, List<BlogTagVO> tags, Long uv, BigDecimal pv, Long likesCount, Long commentsCount) {
        this.uuid = uuid;
        this.coverImage = coverImage;
        this.title = title;
        this.summary = summary;
        this.original = original;
        this.user = user;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.firstId = firstId;
        this.type = type;
        this.tags = tags;
        this.uv = uv;
        this.pv = pv;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public BlogUserVO getUser() {
        return user;
    }

    public void setUser(BlogUserVO user) {
        this.user = user;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public BlogTypeVO getType() {
        return type;
    }

    public void setType(BlogTypeVO type) {
        this.type = type;
    }

    public List<BlogTagVO> getTags() {
        return tags;
    }

    public void setTags(List<BlogTagVO> tags) {
        this.tags = tags;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }
}
