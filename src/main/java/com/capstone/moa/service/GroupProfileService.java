package com.capstone.moa.service;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupProfile;
import com.capstone.moa.repository.GroupProfileRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GroupProfileService {

    private final GroupProfileRepository groupProfileRepository;
    private final GroupRepository groupRepository;

    public void uploadGroupProfile(MultipartFile file, Long groupId) throws Exception {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        GroupProfile groupProfile = ImageUtils.parseImageInfo(file);

        if (groupProfile == null) {
            throw new IllegalStateException("저장할 데이터가 없습니다");
        }

        groupProfile.setGroup(group);
        groupProfileRepository.save(groupProfile);
        group.addGroupProfile(groupProfile);
    }

    public String downloadImage(Long groupId) throws IOException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        GroupProfile groupProfile = null;
        if (group.getGroupProfile() != null) {
            groupProfile = group.getGroupProfile();
        } else {
            return null;
        }

        String filePath = groupProfile.getImgPath();
        byte[] downloadImage = Files.readAllBytes(new File(filePath).toPath());
        return Base64.getEncoder().encodeToString(downloadImage);
    }
}
