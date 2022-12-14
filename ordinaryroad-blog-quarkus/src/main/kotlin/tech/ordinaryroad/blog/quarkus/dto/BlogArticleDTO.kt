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
package tech.ordinaryroad.blog.quarkus.dto

import cn.hutool.core.util.BooleanUtil
import cn.hutool.core.util.StrUtil
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.quarkus.runtime.annotations.RegisterForReflection
import tech.ordinaryroad.blog.quarkus.dal.entity.BlogArticle
import tech.ordinaryroad.blog.quarkus.service.BlogTagService
import tech.ordinaryroad.blog.quarkus.service.BlogTypeService
import javax.enterprise.inject.spi.CDI

/**
 * 博客文章DTO类
 */
@JsonInclude
@JsonPropertyOrder
@RegisterForReflection
data class BlogArticleDTO(
    var coverImage: String = StrUtil.EMPTY,
    var title: String = StrUtil.EMPTY,
    var summary: String = StrUtil.EMPTY,
    var content: String = StrUtil.EMPTY,
    var canComment: Boolean = true,
    var canReward: Boolean = false,
    var original: Boolean = false,
    var status: String = StrUtil.EMPTY,
    var firstId: String = StrUtil.EMPTY,
    var typeName: String = StrUtil.EMPTY,
    var tagNames: List<String> = emptyList(),
) : BaseBlogModelDTO<BlogArticle>() {
    override fun parse(baseDo: BlogArticle) {
        coverImage = StrUtil.nullToEmpty(baseDo.coverImage)
        title = StrUtil.nullToEmpty(baseDo.title)
        summary = StrUtil.nullToEmpty(baseDo.summary)
        content = StrUtil.nullToEmpty(baseDo.content)
        canComment = BooleanUtil.isTrue(baseDo.canComment)
        canReward = BooleanUtil.isTrue(baseDo.canReward)
        original = BooleanUtil.isTrue(baseDo.original)
        status = baseDo.status.name
        firstId = StrUtil.nullToEmpty(baseDo.firstId)

        val typeId = baseDo.typeId
        if (!typeId.isNullOrBlank()) {
            val typeService = CDI.current().select(BlogTypeService::class.java).get()
            typeService.findById(typeId)?.let {
                typeName = it.name
            }
        }
        val tagIds = baseDo.tagIds
        if (!tagIds.isNullOrEmpty()) {
            val tagService = CDI.current().select(BlogTagService::class.java).get()
            tagNames = tagService.dao.selectNameListByUuidIn(tagIds)
        }
    }

    companion object {
        private const val serialVersionUID: Long = -1010217906295586636L
    }
}
