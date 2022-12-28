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

package tech.ordinaryroad.blog.quarkus.state.article

import tech.ordinaryroad.blog.quarkus.dal.entity.BlogArticle
import tech.ordinaryroad.blog.quarkus.enums.BlogArticleStatus
import tech.ordinaryroad.blog.quarkus.state.article.base.ArticleState
import javax.enterprise.context.ApplicationScoped

/**
 * 待审核
 *
 * @author mjz
 * @date 2022/11/23
 */
@ApplicationScoped
class PendingState : ArticleState() {

    override fun startAuditing(article: BlogArticle) {
        /* 更新状态 */
        super.articleService.updateStatusByFirstIdAndCreatedBy(
            article.firstId,
            article.createBy,
            BlogArticleStatus.PENDING,
            BlogArticleStatus.UNDER_REVIEW
        )

        /* 开始审核邮件通知 */
        super.pushService.startAuditingArticle(article)
    }
}