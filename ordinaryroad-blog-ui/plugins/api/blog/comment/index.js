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

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  apis: {
    post: ({
      articleId,
      content,
      parentId = ''
    }) => {
      return $axios({
        url: '/api/blog/comment/post',
        method: 'post',
        data: {
          articleId,
          content,
          parentId: parentId || ''
        }
      })
    },
    pageArticle: (articleId, page) => {
      return $axios({
        url: `/api/blog/comment/page/article/${articleId}/${page}/20?sortBy=createdTime&sortDesc=true`,
        method: 'get'
      })
    },
    pageSub: (originalId, page) => {
      return $axios({
        url: `/api/blog/comment/page/sub/${originalId}/${page}/5?sortBy=createdTime&sortDesc=true`,
        method: 'get'
      })
    }
  }
}