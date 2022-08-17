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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers
import tech.ordinaryroad.blog.quarkus.dao.BlogArticleDAO
import tech.ordinaryroad.blog.quarkus.entity.BlogArticle
import tech.ordinaryroad.blog.quarkus.enums.BlogArticleStatus
import tech.ordinaryroad.commons.mybatis.quarkus.service.BaseService
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BlogArticleService : BaseService<BlogArticleDAO, BlogArticle>() {

    /**
     * 根据状态查询所有最初版本的文章
     */
    fun findAllFirstArticleByStatusAndCreateBy(state: BlogArticleStatus, createBy: String): List<BlogArticle> {
        return super.dao.selectAllFirstArticleByStateAndCreateBy(state, createBy)
    }

    /**
     * 根据状态查询文章
     */
    fun findAllByStatus(status: BlogArticleStatus): List<BlogArticle> {
        val wrapper = Wrappers.query<BlogArticle>()
        wrapper.eq("status", status)
        return super.dao.selectList(wrapper)
    }

    /**
     * 根据Id和状态查询文章
     */
    fun findByIdAndStatus(id: String, status: BlogArticleStatus): BlogArticle? {
        val wrapper = Wrappers.query<BlogArticle>()
        wrapper.eq("uuid", id)
        wrapper.eq("status", status)
        return super.dao.selectOne(wrapper)
    }

    /**
     * 根据Id查询用户创建的文章
     */
    fun findByIdAndCreatedBy(id: String, createBy: String): BlogArticle? {
        val wrapper = Wrappers.query<BlogArticle>()
        wrapper.eq("uuid", id)
        wrapper.eq("create_by", createBy)
        return super.dao.selectOne(wrapper)
    }

    /**
     * 根据PreId和状态查询最新的或最旧的文章
     */
    fun findFirstOrLastByFirstIdAndStatus(
        firstId: String,
        status: BlogArticleStatus,
        first: Boolean = true
    ): BlogArticle? {
        val wrapper = ChainWrappers.queryChain(super.dao)
        wrapper.eq("first_id", firstId)
        wrapper.eq("status", status)
        wrapper.orderBy(true, first, "created_time")
        val page = wrapper.page(Page(1, 1, 1))
        return page.records.firstOrNull()
    }

    /**
     * 根据PreId和状态查询用户创建的文章
     */
    fun findByPreIdAndStatusAndCreatedBy(firstId: String, status: BlogArticleStatus, createBy: String): BlogArticle? {
        val wrapper = Wrappers.query<BlogArticle>()
        wrapper.eq("first_id", firstId)
        wrapper.eq("status", status)
        wrapper.eq("create_by", createBy)
        return super.dao.selectOne(wrapper)
    }

    /**
     * 根据状态查询所有用户创建的文章
     */
    fun findAllByStatusAndCreatedBy(status: BlogArticleStatus, createBy: String): List<BlogArticle> {
        val wrapper = Wrappers.query<BlogArticle>()
        wrapper.eq("status", status)
        wrapper.eq("create_by", createBy)
        return super.dao.selectList(wrapper)
    }

    /**
     * 根据状态查询所有用户创建的文章个数
     */
    fun countByStatusAndCreatedBy(status: BlogArticleStatus, createBy: String): Long {
        val wrapper = Wrappers.query<BlogArticle>()
        wrapper.eq("status", status)
        wrapper.eq("create_by", createBy)
        return super.dao.selectCount(wrapper)
    }

    /**
     * 根据Id更新状态
     */
    fun updateStatusById(id: String, status: BlogArticleStatus) {
        super.update(BlogArticle().apply {
            this.uuid = id
            this.status = status
        })
    }

    /**
     * 根据FirstId和CreatedBy和状态更新状态
     */
    fun updateStatusByFirstIdAndCreatedBy(
        firstId: String,
        createBy: String,
        oldStatus: BlogArticleStatus,
        newStatus: BlogArticleStatus
    ) {
        val wrapper = Wrappers.update<BlogArticle>()
            .eq("first_id", firstId)
            .eq("create_by", createBy)
            .eq("status", oldStatus)
        super.update(BlogArticle().apply {
            this.status = newStatus
        }, wrapper)
    }

}