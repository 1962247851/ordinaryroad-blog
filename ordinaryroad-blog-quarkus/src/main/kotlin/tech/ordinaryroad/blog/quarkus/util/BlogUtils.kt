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

package tech.ordinaryroad.blog.quarkus.util

import cn.dev33.satoken.stp.StpUtil

/**
 *
 *
 * @author mjz
 * @date 2022/9/1
 */
object BlogUtils {

    /**
     * 解析两个List的差异
     *
     * @return list[0]: 需要删除的
     * list[1]: 需要添加的
     */
    fun <E> List<E>.differ(newList: List<E>): List<List<E>> {
        val oldList = this

        val intersectSet = newList.intersect(oldList.toSet())

        val listToAdd = arrayListOf<E>()
        val listToDelete = arrayListOf<E>()
        oldList.forEach {
            if (!intersectSet.contains(it)) {
                listToDelete.add(it)
            }
        }
        newList.forEach {
            if (!intersectSet.contains(it)) {
                listToAdd.add(it)
            }
        }

        return arrayListOf(listToDelete, listToAdd)
    }

    fun checkAdminOrDeveloper() {
        StpUtil.checkRoleOr("DEVELOPER", "ADMIN")
    }

}