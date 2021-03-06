package cn.skill6.website.dao.impl.other;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.skill6.common.entity.po.other.VersionInfo;
import cn.skill6.common.exception.general.NullPointerException;
import cn.skill6.website.dao.intf.other.VersionInfoDao;
import cn.skill6.website.dao.mappers.other.VersionInfoMapper;
import cn.skill6.website.util.sequence.SequenceManager;

/**
 * 版本信息操作实现类
 *
 * @author 何明胜
 * @version 1.3
 * @since 2018年8月28日 上午12:20:18
 */
public class VersionInfoDaoOper implements VersionInfoDao {
  private static final Logger logger = LoggerFactory.getLogger(VersionInfoDaoOper.class);

  @Autowired private VersionInfoMapper versionInfoMapper;

  @Override
  public int deleteByVersionId(Long versionId) {
    logger.warn("删除id为{}的版本信息", versionId);

    return versionInfoMapper.deleteByPrimaryKey(versionId);
  }

  @Override
  public void addVersionInfo(VersionInfo versionInfo) {
    if (versionInfo.getVersionUserId() == null) {
      throw new NullPointerException("版本发布用户id不能为null");
    }
    if (versionInfo.getVersionNumber() == null) {
      throw new NullPointerException("版本号不能为null");
    }
    if (versionInfo.getVersionContent() == null) {
      throw new NullPointerException("版本内容不能为null");
    }

    Long versionId = versionInfo.getVersionId();
    if (versionId == null) {
      versionId = SequenceManager.getNextId();
    }

    versionInfo.setVersionReleaseTime(new Date());
    versionInfoMapper.insert(versionInfo);
    logger.info("成功添加id为{}的版本信息,{}", versionId, versionInfo);
  }

  @Override
  public VersionInfo findByVersionId(Long versionId) {
    if (versionId == null) {
      throw new NullPointerException("版本id不能为null");
    }

    VersionInfo versionInfo = versionInfoMapper.selectByPrimaryKey(versionId);
    logger.info("成功找到id为{}的版本信息,{}", versionId, versionInfo);

    return versionInfo;
  }

  @Override
  public List<VersionInfo> findAll() {
    List<VersionInfo> versionInfos = versionInfoMapper.selectAll();
    logger.info("找到所有版本信息,{}", versionInfos);

    return versionInfos;
  }

  @Override
  public void modifyByVersionId(VersionInfo versionInfo) {
    if (versionInfo.getVersionId() == null) {
      throw new NullPointerException("版本id不能为null");
    }

    if (versionInfo.getVersionUserId() == null) {
      throw new NullPointerException("版本发布用户id不能为null");
    }
    if (versionInfo.getVersionNumber() == null) {
      throw new NullPointerException("版本号不能为null");
    }
    if (versionInfo.getVersionContent() == null) {
      throw new NullPointerException("版本内容不能为null");
    }

    versionInfoMapper.updateByPrimaryKey(versionInfo);
    logger.info("成功修改版本信息为{}",versionInfo);
  }
}
