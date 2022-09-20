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
package tech.ordinaryroad.blog.quarkus.service

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import io.quarkus.arc.Unremovable
import tech.ordinaryroad.blog.quarkus.dal.dao.BlogUserLikedArticleDAO
import tech.ordinaryroad.blog.quarkus.dal.entity.BlogUserLikedArticle
import tech.ordinaryroad.commons.mybatis.quarkus.service.BaseService
import javax.enterprise.context.ApplicationScoped

/**
 * Service-BlogUserLikedArticle
 *
 * @author mjz
 * @date 2022/9/19
 */
@Unremovable
@ApplicationScoped
class BlogUserLikedArticleService : BaseService<BlogUserLikedArticleDAO, BlogUserLikedArticle>() {

    /**
     * 获取文章点赞个数
     */
    fun getLikesCount(articleId: String): Long {
        val wrapper = Wrappers.query<BlogUserLikedArticle>()
            .eq("article_id", articleId)

        return super.dao.selectCount(wrapper)
    }

    /**
     * 点赞文章
     */
    fun likesArticle(articleId: String) {
        super.create(BlogUserLikedArticle().apply {
            setArticleId(articleId)
        })
    }

    /**
     * 取消点赞
     */
    fun unlikesArticle(userId: String, articleId: String) {
        val wrapper = Wrappers.query<BlogUserLikedArticle>()
            .eq("create_by", userId)
            .eq("article_id", articleId)

        super.dao.delete(wrapper)
    }

    /**
     * 获取是否已点赞
     */
    fun getLiked(userId: String, articleId: String): Boolean {
        val wrapper = Wrappers.query<BlogUserLikedArticle>()
            .eq("create_by", userId)
            .eq("article_id", articleId)

        return super.dao.exists(wrapper)
    }

}