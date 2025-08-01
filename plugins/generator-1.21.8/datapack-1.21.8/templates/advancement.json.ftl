<#-- @formatter:off -->
<#include "mcitems.ftl">
{
    <#if !data.disableDisplay>
        "display": {
          <#if data.parent == "none" && !data.parent.toString().contains("@")>
              <#if !data.background?has_content || data.background == "Default">
                  "background": "minecraft:block/stone",
              <#else>
                  "background": "${modid}:screens/${data.background?keep_before_last(".png")}",
              </#if>
          </#if>
          "icon": {
            ${mappedMCItemToItemObjectJSON(data.achievementIcon, "id")}
          },
          <#if generator.getGeneratorFlavor() == "DATAPACK">
          "title": "${data.achievementName}",
          "description": "${data.achievementDescription}",
          <#else>
          "title": {
            "translate": "advancements.${registryname}.title"
          },
          "description": {
            "translate": "advancements.${registryname}.descr"
          },
          </#if>
          "frame": "${data.achievementType}",
          "show_toast": ${data.showPopup},
          "announce_to_chat": ${data.announceToChat},
          "hidden": ${data.hideIfNotCompleted}
        },
    </#if>
    "criteria": {
      ${triggercode?keep_before_last(",")}
    }
    <#if data.hasRewards()>,
    "rewards": {
        "experience": ${data.rewardXP}

        <#if data.rewardFunction?has_content>,
        "function": "${generator.getResourceLocationForModElement(data.rewardFunction)}"
        </#if>

        <#if data.rewardLoot?has_content>,
        "loot": [
            <#list data.rewardLoot as value>
                "${generator.getResourceLocationForModElement(value)}"<#sep>,
            </#list>
        ]
        </#if>

        <#if data.rewardRecipes?has_content>,
        "recipes": [
            <#list data.rewardRecipes as value>
                "${generator.getResourceLocationForModElement(value)}"<#sep>,
            </#list>
        ]
        </#if>
    }
    </#if>
    <#if data.parent?has_content && data.parent != "none" && !data.parent.toString().contains("@")>,
    "parent": "${data.parent}"
    </#if>
}
<#-- @formatter:on -->