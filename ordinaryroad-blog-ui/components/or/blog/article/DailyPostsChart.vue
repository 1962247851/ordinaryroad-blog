<!--
  - MIT License
  -
  - Copyright (c) 2021 苗锦洲
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

<template>
  <v-card :loading="loading" flat :outlined="outlined">
    <v-card-title>
      {{ $t('myStats.charts.dailyPosts') }}
      <v-btn
        icon
        small
        class="ms-2"
        :loading="refreshing||loading"
        @click="refresh"
      >
        <v-icon>
          mdi-refresh
        </v-icon>
      </v-btn>
      <v-spacer />
      <v-select
        v-model="selectedYear"
        outlined
        style="position: absolute; top: 12px; right: 20px; width: 150px"
        hide-details
        dense
        :items="articlePublishedYearsOption.data"
        :loading="articlePublishedYearsOption.loading"
      />
    </v-card-title>
    <div v-if="!loading">
      <div
        v-if="options.series.data.length"
        ref="div"
        :style="{
          width: '100%',
          height: vertical?'800px':'200px'
        }"
      />
      <div v-else>
        <or-empty
          :style="{
            width: '100%',
            height: vertical?'800px':'200px'
          }"
        />
      </div>
    </div>
    <div v-else>
      <v-skeleton-loader
        v-for="i in vertical?4:1"
        :key="i"
        tile
        height="200"
        type="image"
      />
    </div>
  </v-card>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'OrBlogArticleDailyPostsChart',
  props: {
    vertical: {
      type: Boolean,
      default: false
    },
    outlined: {
      type: Boolean,
      default: true
    },
    createBy: {
      type: String,
      default: ''
    }
  },
  data: () => ({
    refreshing: false,
    loading: true,
    selectedYear: null,
    articlePublishedYearsOption: {
      loading: true,
      data: []
    },
    dailyPostsCount: [],
    chart: undefined,
    options: {
      tooltip: {},
      visualMap: {
        showLabel: true,
        splitNumber: 5,
        min: 0,
        max: null,
        type: 'piecewise',
        orient: 'horizontal',
        left: 'center',
        top: null,
        bottom: 0,
        text: ['More', 'Less']
      },
      calendar: {
        orient: 'horizontal',
        top: 20,
        left: 30,
        right: 10,
        bottom: 30,
        cellSize: ['auto', 'auto'],
        range: null,
        itemStyle: {
          borderWidth: 0.5
        },
        yearLabel: { show: false }
      },
      series: {
        type: 'heatmap',
        coordinateSystem: 'calendar',
        data: []
      }
    }
  }),
  computed: {
    ...mapGetters('user', {
      userInfo: 'getUserInfo'
    })
  },
  watch: {
    vertical (val) {
      if (this.chart) {
        this.updateOptionsOrient(val)
        this.chart.setOption(this.options)
      }
    },
    selectedYear (val) {
      this.options.calendar.range = val
      this.getData()
    }
  },
  created () {
    this.$apis.blog.article.getArticlePublishedYears({ userId: this.createBy })
      .then((data) => {
        this.articlePublishedYearsOption.loading = false
        this.articlePublishedYearsOption.data = data
        if (data.length) {
          this.selectedYear = data[data.length - 1]
        } else {
          this.loading = false
        }
      })
      .catch(() => {
        this.articlePublishedYearsOption.loading = false
      })
  },
  mounted () {
  },
  methods: {
    refresh () {
      this.loading = true
      this.refreshing = true
      this.articlePublishedYearsOption.loading = true
      this.getSelections()
      if (this.selectedYear) {
        this.getData()
      }
    },
    updateOptionsOrient (vertical) {
      const orient = vertical ? 'vertical' : 'horizontal'
      // this.options.visualMap.showLabel = !vertical
      this.options.visualMap.showLabel = false
      this.options.visualMap.top = vertical ? 0 : null
      this.options.visualMap.bottom = vertical ? null : 0
      this.options.calendar.orient = orient
      this.options.calendar.top = (vertical ? 50 : 20)
      this.options.calendar.bottom = vertical ? 10 : 30
    },
    getSelections () {
      this.$apis.blog.article.getArticlePublishedYears({ userId: this.createBy })
        .then((data) => {
          this.articlePublishedYearsOption.loading = false
          this.articlePublishedYearsOption.data = data
          if (data.length) {
            this.selectedYear = data[data.length - 1]
          } else {
            this.loading = false
          }
          this.refreshing = false
        })
        .catch(() => {
          this.articlePublishedYearsOption.loading = false
          this.refreshing = false
        })
    },
    getData () {
      this.$apis.blog.article.countDailyPosts({
        startDateTime: `${this.selectedYear}-01-01T00:00:00`,
        endDateTime: `${this.selectedYear}-12-31T23:59:59`,
        userId: this.createBy || this.userInfo.user.uuid
      })
        .then((data) => {
          this.options.series.data = []
          let maxCount = 0
          data.forEach((item) => {
            if (maxCount < item.count) {
              maxCount = item.count
            }
            this.options.series.data.push([
              item.date,
              item.count
            ])
          })
          let max = maxCount
          const i = maxCount < 10 ? 2 : 10
          while (max % i) {
            max++
          }
          this.options.visualMap.max = max
          this.options.visualMap.splitNumber = Math.min(max / 2, 5)
          this.updateOptionsOrient(this.vertical)
          this.dailyPostsCount = data
          this.loading = false
          this.refreshing = false

          if (this.options.series.data.length) {
            this.$nextTick(() => {
              this.chart = this.$echarts.init(this.$refs.div)
              window.addEventListener('resize', this.chart.resize, true)
              this.chart.setOption(this.options)
            })
          }
        })
        .catch(() => {
          this.loading = false
          this.refreshing = false
        })
    }
  }
}
</script>

<style scoped>

</style>
