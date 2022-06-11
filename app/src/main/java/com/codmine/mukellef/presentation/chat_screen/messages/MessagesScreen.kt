package com.codmine.mukellef.presentation.chat_screen.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.util.Constants.XL_ROUNDED_VALUE
import com.codmine.mukellef.domain.util.postDate
import com.codmine.mukellef.domain.util.postTime
import com.codmine.mukellef.presentation.chat_screen.messages.components.DayHeader
import com.codmine.mukellef.presentation.chat_screen.messages.components.JumpToBottom
import com.codmine.mukellef.presentation.chat_screen.messages.components.MessageHeader
import com.codmine.mukellef.presentation.chat_screen.messages.components.MessageInput
import com.codmine.mukellef.presentation.components.DataNotFound
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.presentation.util.UiText
import com.codmine.mukellef.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun MessagesScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: MessagesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusRequester = FocusRequester()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(MessagesEvent.LoadData, context)
    }

   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(paddingValues)
   ) {
       MessageHeader(
           title = state.receiverName,
           modifier = Modifier.padding(MaterialTheme.spacing.medium),
       )

       Column(
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {

           Box(
               modifier = Modifier.weight(.85f),
           ) {
               LazyColumn(
                   reverseLayout = true,
                   state = scrollState,
               ) {
                   for (i in state.messages.indices) {
                       item{
                           if ((state.messages[i].readingTime.isEmpty()) && (state.messages[i].senderUserId == state.receiverId)) {
                               viewModel.onEvent(MessagesEvent.PostReadingMessage(state.messages[i].id), context)
                           }
                           MessageItem(state.messages[i], state.messages[i].senderUserId == state.userId)
                           if (i + 1 < state.messages.size) {
                               if (postDate(state.messages[i].postTime) != postDate(state.messages[i + 1].postTime)) {
                                   DayHeader(postDate(state.messages[i].postTime))
                               }
                           } else {
                               DayHeader(postDate(state.messages[i].postTime))
                           }
                       }
                   }
               }

               // Jump to bottom button shows up when user scrolls past a threshold.
               // Convert to pixels:
               val jumpThreshold = with(LocalDensity.current) {
                   JumpToBottomThreshold.toPx()
               }

               // Show the button if the first visible item is not the first one or if the offset is
               // greater than the threshold.
               val jumpToBottomButtonEnabled by remember {
                   derivedStateOf {
                       scrollState.firstVisibleItemIndex != 0 ||
                               scrollState.firstVisibleItemScrollOffset > jumpThreshold
                   }
               }

               JumpToBottom(
                   // Only show if the scroller is not at the bottom
                   enabled = jumpToBottomButtonEnabled,
                   onClicked = {
                       scope.launch {
                           scrollState.animateScrollToItem(0)
                       }
                   },
                   modifier = Modifier.align(Alignment.BottomCenter)
               )
           }

           MessageInput(
               text = state.message,
               modifier = Modifier.weight(.15f),
               focusRequester = focusRequester,
               onValueChange = {
                   viewModel.onEvent(MessagesEvent.MessageChanged(it), context)
               },
               sendMessage = {
                   scope.launch {
                       viewModel.onEvent(MessagesEvent.PostMessage(it), context)
                       scrollState.scrollToItem(0)
                   }
               }
           )

           if((!state.isLoading) && ((state.error.isBlank())) && (state.messages.isEmpty())) {
               DataNotFound(message = UiText.StringResources(R.string.messages_not_found).asString())
           }
           if(state.error.isNotBlank()) {
               ReLoadData(
                   modifier = Modifier.fillMaxSize(),
                   errorMsg = state.error,
                   onRetry = {
                       viewModel.onEvent(MessagesEvent.Refresh, context)
                   }
               )
           }
           if(state.isLoading) {
               Box(modifier = Modifier.fillMaxSize()) {
                   CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
               }
           }

       }

   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageItem(
    message: Message,
    isUserMe: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal =  MaterialTheme.spacing.medium,
                vertical =  MaterialTheme.spacing.small,
            ),
        horizontalAlignment = when {
            isUserMe -> Alignment.End
            else -> Alignment.Start
        }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            text = postTime(message.postTime),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            style = MaterialTheme.typography.bodySmall,
        )
        Card(
            modifier = Modifier.widthIn(max = 265.dp),
            shape = cardShapeFor(isUserMe),
            colors = CardDefaults.cardColors(
                containerColor = when {
                    isUserMe -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.tertiaryContainer
                }
            )
        )
        {
            Text(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.large,
                    vertical = MaterialTheme.spacing.medium
                ),
                text = message.message,
                style = MaterialTheme.typography.bodyLarge,
                color = when {
                    isUserMe -> MaterialTheme.colorScheme.onPrimary
                    else -> MaterialTheme.colorScheme.tertiary
                }
            )
        }
    }
}

@Composable
fun cardShapeFor(isUserMe: Boolean): Shape {
    val roundedCorners = RoundedCornerShape(XL_ROUNDED_VALUE)
    return when {
        isUserMe -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

private val JumpToBottomThreshold = 56.dp