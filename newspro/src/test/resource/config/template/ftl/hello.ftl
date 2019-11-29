${user!}你是最棒的-------
<#assign user="2b111111"><#-- 替换一个顶层变量 -->
${user!}你是最棒的。you will conquer the world.
<#assign user=4>
<#list lis as n>
	遍历list--> ${n}
</#list>
${subs[1..4]}
<#assign keys=map?keys>
	<#list keys as c>
		遍历map--> key:  ${c} 国家:${map["${c}"]["country"]} 城市：${map["${c}"].city}
	</#list>
	
${"helloo${user}"}
${"hello"+user!}

<#assign a="asd">
${a?cap_first}<#--把a的第一个首字母变为大写-->

<#if te ??>
	存在
<#else>
 	不存在
 </#if>