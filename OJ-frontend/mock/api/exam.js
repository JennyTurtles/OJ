const examList = [
  {
    allowChangeSeat: true,
    canGetHint: true,
    description: '尝试提交各种运行结果的代码，了解在线考试提交的流程及查看系统裁判结果。',
    endtime: '2040-12-30 23:55:00',
    examType: 'practice',
    id: 26,
    language: 'C,C++,Java',
    name: '在线考试操作示例',
    page: 1,
    partialScore: true,
    problemNum: 2,
    rows: 8,
    starttime: '2019-12-24 11:15:00',
    status: '考试进行中',
    studentViewScore: 'System',
    submitOnlyAC: false,
    teacherId: 1,
    teacherName: '黄秋波',
    type: 'practice',
    updateTime: '2022-06-16 17:43:09',
  },
  {
    allowChangeSeat: true,
    canGetHint: true,
    description:
      '推荐使用mingw的g++编译器（C++14标准）进行编程，编译器不同导致运行结果不同非常正常。',
    endtime: '2023-09-18 23:55:00',
    examType: 'practice',
    id: 313,
    language: 'C++',
    name: '2023力扣练习',
    page: 1,
    partialScore: true,
    problemNum: 100,
    rows: 8,
    starttime: '2023-07-07 00:00:00',
    status: '考试已结束',
    studentViewScore: 'System',
    submitOnlyAC: true,
    teacherId: 1,
    teacherName: '黄秋波',
    type: 'practice',
    updateTime: '2023-09-13 16:46:05',
  },
  {
    allowChangeSeat: true,
    canGetHint: false,
    description: '非AC可以提交；不可查看测试数据',
    endtime: '2023-09-08 23:00:00',
    examType: 'practice',
    id: 318,
    language: 'C++',
    name: '高级程序设计测验3',
    page: 1,
    partialScore: true,
    problemNum: 3,
    rows: 8,
    starttime: '2023-09-08 18:00:00',
    status: '考试已结束',
    studentViewScore: 'System',
    submitOnlyAC: false,
    teacherId: 1,
    teacherName: '黄秋波',
    type: 'practice',
    updateTime: '2023-09-07 21:37:26',
  },
  {
    allowChangeSeat: true,
    canGetHint: false,
    description: '非AC也可提交；不可查看测试数据',
    endtime: '2023-09-01 18:00:00',
    examType: 'practice',
    id: 317,
    language: 'C++',
    name: '高级程序设计测验2',
    page: 1,
    partialScore: true,
    problemNum: 3,
    rows: 8,
    starttime: '2023-09-01 13:00:00',
    status: '考试已结束',
    studentViewScore: 'System',
    submitOnlyAC: false,
    teacherId: 1,
    teacherName: '黄秋波',
    type: 'practice',
    updateTime: '2023-09-01 12:57:38',
  },
  {
    allowChangeSeat: true,
    canGetHint: false,
    description:
      '没有AC也可提交，只要看到有分，提交本题后可将分数加到总分中。考试结束后也可提交本题（不可再提交代码）。',
    endtime: '2023-07-28 20:00:00',
    examType: 'practice',
    id: 315,
    language: 'C++',
    name: '高级程序设计测验1',
    page: 1,
    partialScore: true,
    problemNum: 2,
    rows: 8,
    starttime: '2023-07-28 13:00:00',
    status: '考试已结束',
    studentViewScore: 'System',
    submitOnlyAC: false,
    teacherId: 1,
    teacherName: '黄秋波',
    type: 'practice',
    updateTime: '2023-07-28 09:11:17',
  },
  {
    allowChangeSeat: true,
    canGetHint: true,
    description:
      '推荐使用mingw的g++编译器（C++14标准）进行编程，编译器不同导致运行结果不同非常正常。',
    endtime: '2023-07-12 23:55:00',
    examType: 'practice',
    id: 314,
    language: 'C++',
    name: '2023链表练习',
    page: 1,
    partialScore: true,
    problemNum: 10,
    rows: 8,
    starttime: '2023-06-30 00:00:00',
    status: '考试已结束',
    studentViewScore: 'System',
    submitOnlyAC: true,
    teacherId: 1,
    teacherName: '黄秋波',
    type: 'practice',
    updateTime: '2023-06-30 14:25:55',
  },
]

export default [
  {
    url: '/api/exam',
    method: 'get',
    response: (data = {}) => {
      const { pageNum, pageSize } = data.query
      console.log(data.query)
      let pageData = examList.slice(0, 3)
      // let total = examList.length
      // const filterData = examList
      // if (filterData.length) {
      //   if (pageSize) {
      //     while (pageData.length < pageSize) {
      //       pageData.push(filterData[Math.round(Math.random() * (filterData.length - 1))])
      //     }
      //   } else {
      //     pageData = filterData
      //   }
      //   pageData = pageData.map((item, index) => ({
      //     id: pageSize * (pageNum - 1) + index + 1,
      //     ...item,
      //   }))
      // } else {
      //   total = 0
      // }
      return {
        code: 0,
        message: 'ok',
        data: {
          list: pageData,
          pageNum,
          pageSize,
          total: 3,
        },
      }
    },
  },
  {
    url: '/api/exam/take',
    method: 'post',
    response: ({ body }) => {
      const { id } = body
      if (!id)
        return {
          code: 400,
          message: 'fail',
        }
      return {
        code: 0,
        message: 'ok',
        data: {},
      }
    },
  },
  {
    url: '/api/exam/sync_time',
    method: 'get',
    response: () => {
      return {
        code: 0,
        message: 'ok',
        data: {
          leftTime: 6757,
        },
      }
    },
  },
]
