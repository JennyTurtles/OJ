<template>
  <CommonPage show-footer >

    <!-- 所有问题 -->
		<div id="faq-allProblems">
			<n-collapse default-expanded-names="1" accordion>
        <n-collapse-item title="动态类型" name="1">
  			  <div class="panel panel-default">
    				<div class="panel-heading" role="tab" id="heading1">
      					<h4 class="panel-title">
        					<a role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse1" aria-expanded="true" aria-controls="collapse1">
          						1. Online Judge（以下简称OJ）支持哪些语言？
        					</a>
      					</h4>
    				</div>
				    <div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading1">
				      	<div class="panel-body">
				        	<p>回答：目前为止，OJ系统支持C、C++和Java三种语言。具体一场考试支持哪些语言，需要在提交代码的界面查看“编程语言”下拉框，从中选择一门语言完成编码。</p>
				      	</div>
				    </div>
				 </div>
        </n-collapse-item>
        <n-collapse-item title="动态类型" name="2">
				  <div class="panel panel-default">
				 	<div class="panel-heading" role="tab" id="heading2">
				      	<h4 class="panel-title">
				        	<a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse2" aria-expanded="false" aria-controls="collapse2">
				          		2. 如何向OJ系统提交程序？
				        	</a>
				      	</h4>
				   	</div>
	    			<div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading2">
	      				<div class="panel-body">
	        				<p>回答：提交程序分两步：</p>
	        				<ol>
							  	<li>
							  		<h4>第1步：提交代码。</h4>
							  		<p>进入一场考试后，从左侧题目列表中选中一个题目，此时将打开该题。进入“代码提交”页（如下图界面），将已编好的代码复制到“源代码”框中，并选择“编程语言”，输入验证码，然后点击“提交代码。</p>
	        						<p><img class="shadow-img" alt="" src="@/assets/images/faq2-1.png"></p>
	        						<p>提交代码后，后台的裁判机进行裁判（此过程可能需要几秒钟），之后可看到运行结果（如下图界面）：</p>
	        						<p><img class="shadow-img" alt="" src="@/assets/images/faq2-2.png"></p>
	        						<p>这个结果显示：</p>
	        						<ul>
	        							<li>提交代码的运行结果为“WA”，表示结果错误，从界面下部的“正确测试用例ID”可知，其中有两个测试用例执行正确，而“错误测试用例ID”显示有三个测试用例执行错误。</li>
	        							<li>本题的5个测试用例，其中两个运行正确，如果此时“提交本题”，本题“所得分数”为0.85分。</li>
	        						</ul>
							  	</li>
							  	<li>
							  		<h4>第二步：提交本题。</h4>
							  		<p>以上界面中，如果不想继续修改本题代码，可点“提交本题”，将本题交卷。</p>							  		
							  		<p>提交本题后，将产生以下两个影响：</p>
							  		<ul>
							  			<li>本题所得分数真正被计入本场考试的得分，成绩排名也将相应变动。</li>
							  			<li>本题不可再提交代码。</li>
							  		</ul>
									<p>需要注意的是：</p>
									<ul>
										<li>如果本题裁判结果为AC（即完全正确），“提交本题”的步骤将自动完成。</li>
										<li>有些考试，教师设置为只有AC的代码才能“提交本题”，因此，像以上所示WA的代码“提交本题”时，系统将提示：
											<img class="shadow-img" alt="" src="@/assets/images/faq2-3.png">
										</li>				
									</ul>
							  	</li>
							</ol>	        		     					
	        			</div>
	    			</div>
  				</div>
        </n-collapse-item>
        <n-collapse-item title="动态类型" name="3">
	  			<div class="panel panel-default">
				    <div class="panel-heading" role="tab" id="heading3">
				      	<h4 class="panel-title">
				        	<a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse3" aria-expanded="false" aria-controls="collapse3">
				          		3. 我提交了程序，OJ回复的那些评判结果是什么意思？
				        	</a>
				      	</h4>
					</div>
				    <div id="collapse3" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading3">
				      	<div class="panel-body">
				        	<p>回答：下面是常见的OJ评判结果状态以及它们表示的意思：</p>
				        	<ul>
				        		<li>
				        			WAIT : 已经提交到后台系统，正在等待裁判机的评判。不过这个过程一般只持续几秒钟，除非当前同时提交的代码太多。
				        		</li>
				        		<li>
				        			QUEUE : 您提交的代码已经进入裁判机，开始被裁判机评判。
				        		</li>
				        		<li>
				        			AC (Accepted) : 您的程序经过严格测试完全正确，恭喜！
				        		</li>
				        		<li>
				        			PE (Presentation Error) : 虽然您的程序貌似输出了正确的结果，但是这个结果的格式有点问题。请检查程序的输出是否多了或者少了空格（' '）、制表符（'\t'）、换行符（'\n'）或者字符串结束符（’\0’）。
				        		</li>
				        		<li>
				        			WA (Wrong Answer) : 输出结果错，这个一般认为是算法有问题。				        			
				        		</li>
				        		<li>
				        			RE (Runtime Error) : 运行时错误，这个一般是程序在运行期间执行了非法的操作造成的。另外，需要特别提醒的是，main函数需要返回0，如果返回其它的值，也会认为是RE哦。				        			
				        		</li>
				        		<li>
				        			TLE (Time Limit Exceeded) : 您的程序运行的时间已经超出了这个题目的时间限制。如果算法不复杂的话，检查一下是否存在死循环。
				        		</li>
				        		<li>
				        			CE (Compilation Error) : 您的程序语法有问题，编译器无法编译。具体的出错信息可以查看“运行结果”页面的“详细信息”框。
				        		</li>
				        		<li>
				        			COPY : Sorry，教师怀疑您抄袭，所以撤销了您的提交啦。				        			
				        		</li>
				        		<li>
				        			REDO: 由于提交的代码不符合题目要求，所以教师要求您重做。
				        		</li>
				        	</ul>
							<p>
								注意，一个程序可能存在多种错误，比如，有些测试用例的运行结果是WA，而有些测试用例的运行结果是PE，OJ系统将按照错误程度严重的那种错误进行提示，所以，将提示您该程序是WA。再比如下图所示结果：				        		
				        	</p>
				        	<p><img class="shadow-img" alt="" src="@/assets/images/faq3-1.png"></p>
				       		<p> 
				       			图中显示运行状态是RE，从“错误测试用例ID”来看，有三个测试用例发生了错误，从“详细信息”来看，其中的2897和2899两个测试用例发生了“运行时错误”，那么，另外一个错误的测试用例2894，可能是WA或者PE之类的。
				       		</p>
				        </div>
				  	</div>
          </div>
        </n-collapse-item>
        
        <n-collapse-item title="动态类型" name="4">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading4">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse4" aria-expanded="false" aria-controls="collapse4">
                        4. 为什么我的程序在VC++/VC下能正常编译，提交到OJ系统就会出现'Compilation Error'？
                    </a>
                  </h4>
            </div>
              <div id="collapse4" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading4">
                  <div class="panel-body">
                    <p>回答：OJ系统使用的是GCC/G++编译器，这个和VC/VC++有所不同，例如：</p>
                    <ul>
                      <li>在G++/GCC下'main'函数必须定义成int型，定义成void main会得到'Compilation Error'。</li>
                      <li>循环变量i定义在for语句块之内"for(int i=0...){...}"也会出错。</li>
                      <li>在G++/GCC中itoa不是一个ANSI函数，但是在VC/VC++中是的。</li>
                    </ul>
                  </div>
              </div>
          </div>
        </n-collapse-item>

        <n-collapse-item title="动态类型" name="5">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading5">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse5" aria-expanded="false" aria-controls="collapse5">
                        5. “运行结果”页面“错误的测试用例ID”那些数字后有一个问号是干什么用的？
                    </a>
                  </h4>
            </div>
              <div id="collapse5" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading5">
                  <div class="panel-body">
                    <p>回答：对于有的考试（或者更确切地说，是练习），教师允许学生查看那些出错的测试用例到底是什么。点击问号后，系统将会给出以下提示：测试数据的输入、正确的输出、您程序的输出。</p>
                    <p>但是，请求一个提示，会付出一点点代价，会扣除该题2%的分数，但如果对同一个测试用例反复请求提示，不会重复扣分的哦。</p>
                <p>对于有的考试，教师不允许请求提示，这时，点击问号后，会提示以下信息：</p>
                <p><img class="shadow-img" alt="" src="@/assets/images/faq5-1.png"></p>
                  </div>
              </div>
          </div>
        </n-collapse-item>

        <n-collapse-item title="动态类型" name="6">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading6">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse6" aria-expanded="false" aria-controls="collapse6">
                        6. 重复查看同一个错误的测试用例的提示，会重复扣分吗？
                    </a>
                  </h4>
            </div>
              <div id="collapse6" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading6">
                  <div class="panel-body">
                    <p>回答: 虽然每次点击问号都会提示扣分，但是，重复查看同一个测试用例的提示，不会重复扣分。</p>
                  </div>
              </div>
          </div>
        </n-collapse-item>

        <n-collapse-item title="动态类型" name="7">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading7">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse7" aria-expanded="false" aria-controls="collapse7">
                        7. 我提交的代码明明是我自己写的，但提示我“涉嫌抄袭”，我能“提交本题”吗？
                    </a>
                  </h4>
            </div>
              <div id="collapse7" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading7">
                  <div class="panel-body">
                    <p>回答: 所谓英雄所见略同，如果确实是您自己码出来的，那就大胆提交吧，然后记得和老师说明一下。</p>
                  </div>
              </div>
          </div>
        </n-collapse-item>

        <n-collapse-item title="动态类型" name="8">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading8">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse8" aria-expanded="false" aria-controls="collapse8">
                        8. 为什么我用scanf/printf的组合能得到'AC'的程序换成cin/cout就得到了'TLE'？
                    </a>
                  </h4>
            </div>
              <div id="collapse8" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading8">
                  <div class="panel-body">
                    <p>回答: 一般情况下，C语言运行时I/O函数比C++的效率高一些，所以处理大数据量的时候，建议使用scanf/printf组合。</p>
                  </div>
              </div>
          </div>
        </n-collapse-item>

        <n-collapse-item title="动态类型" name="9">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading9">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse9" aria-expanded="false" aria-controls="collapse9">
                        9. 如何使用64位整数类型？
                    </a>
                  </h4>
            </div>
              <div id="collapse9" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading9">
                  <div class="panel-body">
                    <p>回答：服务器支持64位有符号和无符号类型的整数。 </p>
                    <ul>
                      <li>
                        <p>有符号型64位整数，值域为：-9223372036854775808 .. 9223372036854775807。</p>
                        <p><img class="shadow-img" alt="" src="@/assets/images/faq9-1.png"></p>
                      </li>
                      <li>
                        <p>无符号型64位整数，值域为：0 .. 18446744073709551615。</p>
                        <p><img class="shadow-img" alt="" src="@/assets/images/faq9-2.png"></p>			        			
                      </li>
                    </ul>
                  </div>
              </div>
          </div>
        </n-collapse-item>

        <n-collapse-item title="动态类型" name="10">
          <div class="panel panel-default">
              <div class="panel-heading" role="tab" id="heading10">
                  <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#allProblems-accordion" href="#collapse10" aria-expanded="false" aria-controls="collapse10">
                        10. 为什么我自己运行程序完全正确，到了OJ系统上就出错了？
                    </a>
                  </h4>
            </div>
              <div id="collapse10" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading10">
                  <div class="panel-body">
                    <p>回答：这种情况的原因有多方面，可能的有：</p>
                    <ul>
                      <li>OJ系统测试很严格，是不是有些测试数据您没考虑到？</li>
                      <li>您在运行程序时，最后是怎么退出程序的？是不是点击运行窗口右上角的叉将窗口关闭了？注意，程序输入完毕后，需要按“Ctrl+Z”再回车，此时您的程序能正确结束，才算正确哦。 </li>
                      <li>OJ系统里，main函数的返回值必须为0，否则也判错哦。</li>
                    </ul>
                  </div>
              </div>
          </div>
        </n-collapse-item>
			</n-collapse>
		</div>
  </CommonPage>
</template>

<script setup>

</script>
